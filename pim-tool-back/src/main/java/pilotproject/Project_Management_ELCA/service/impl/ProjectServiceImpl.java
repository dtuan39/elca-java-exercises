package pilotproject.Project_Management_ELCA.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pilotproject.Project_Management_ELCA.model.dto.ProjectDto;
import pilotproject.Project_Management_ELCA.model.dto.ProjectMembersDto;
import pilotproject.Project_Management_ELCA.model.entity.Employee;
import pilotproject.Project_Management_ELCA.model.entity.Project;
import pilotproject.Project_Management_ELCA.model.exception.ConcurrentUpdateException;
import pilotproject.Project_Management_ELCA.model.exception.ProjectNumberExistedException;
import pilotproject.Project_Management_ELCA.repository.EmployeeRepository;
import pilotproject.Project_Management_ELCA.repository.GroupRepository;
import pilotproject.Project_Management_ELCA.repository.ProjectRepository;
import pilotproject.Project_Management_ELCA.service.ProjectService;
import pilotproject.Project_Management_ELCA.util.ApplicationMapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final GroupRepository groupRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ApplicationMapper mapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, GroupRepository groupRepository, EmployeeRepository employeeRepository, ApplicationMapper mapper) {
        this.projectRepository = projectRepository;
        this.groupRepository = groupRepository;
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    @Override
    public Project createProject(ProjectMembersDto projectMembersDto) {
        ProjectDto dto = projectMembersDto.getProjectDto();
        int[] listEmpId = projectMembersDto.getListEmpId();
        var isExisted = projectRepository.findProjectByNumber(dto.getNumber());
        if (isExisted != null) {
            throw new ProjectNumberExistedException("The project number already existed. Please select a different project number");
        }
        Project project = mapper.projectDtoToProjectEntity(dto);
        project.setGroup(groupRepository.findById(dto.getGroupId()).orElseThrow());

        Set<Employee> employeeList = new HashSet<>();
        for (int id : listEmpId) {
            Employee employee = employeeRepository.findById((long) id).orElseThrow();
            employeeList.add(employee);
        }
        project.setEmployeeList(employeeList);

        return projectRepository.save(project);
    }

    @Override
    public Long countProjects() {
        return projectRepository.count();
    }

    @Override
    public List<Project> getProjectsPagination(int limit, int skip) {
        return projectRepository.getProjectsPagination(limit, skip);
    }

    @Override
    public List<Project> searchProjectPagination(String searchText, String status, int limit, int skip) {
        return projectRepository.searchProjectPagination(searchText, status, limit, skip);
    }

    @Override
    public Project addProject(ProjectDto dto) throws ProjectNumberExistedException {
        var existedProject = projectRepository.findProjectByNumber(dto.getNumber());
        if (existedProject != null) {
            throw new ProjectNumberExistedException("The project number already existed. Please select a different project number");
        }
        Project project = mapper.projectDtoToProjectEntity(dto);
        project.setGroup(groupRepository.findById(dto.getGroupId()).orElseThrow());
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public ProjectMembersDto updateProject(ProjectMembersDto dto) {
        try {
            ProjectDto projectDto = dto.getProjectDto();
            int[] listEmpId = dto.getListEmpId();

            Project entity = projectRepository.findProjectByNumber(projectDto.getNumber());

            entityManager.detach(entity);

            entity.setCustomer(projectDto.getCustomer());
            entity.setName(projectDto.getName());
            entity.setStatus(Project.Status.valueOf(projectDto.getStatus().toString()));
            entity.setStartDate(projectDto.getStartDate());
            entity.setEndDate(projectDto.getEndDate());
            entity.setGroup(groupRepository.findById(projectDto.getGroupId()).orElseThrow());
            entity.setVersion(projectDto.getVersion());
            Set<Employee> employeeList = new HashSet<>();
            for (int id : listEmpId) {
                Employee employee = employeeRepository.findById((long) id).orElseThrow();
                employeeList.add(employee);
            }
            entity.setEmployeeList(employeeList);
            Project project = projectRepository.save(entity);

            ProjectMembersDto response = new ProjectMembersDto();
            response.setProjectDto(mapper.projectToProjectDto(project));
            response.setListEmpId(listEmpId);
            return response;
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new ConcurrentUpdateException("The project has been updated by another user. Please refresh the page and try again.");
        }
    }

    @Override
    public List<Project> searchProject(String searchText, String status) {
        return projectRepository.findProjectBySearchTextAndStatus(searchText.trim(), status.trim());
    }

    @Override
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project findProjectByNumber(Integer number) {
        return projectRepository.findProjectByNumber(number);
    }

    @Override
    public int[] findProjectMembersByNumber(Integer number) {
        Project curProject = projectRepository.findProjectByNumber(number);
        Set<Employee> employeeList = curProject.getEmployeeList();
        int[] listEmpId = new int[employeeList.size()];
        int i = 0;
        for (Employee employee : employeeList) {
            listEmpId[i] = employee.getId().intValue();
            i++;
        }
        return listEmpId;
    }

    @Override
    @Transactional
    public void deleteSingleProject(Long id) {
        Project curProject = projectRepository.findById(id).orElseThrow(
                () -> new ConcurrentUpdateException("The project has been deleted by another user. Please refresh the page and try again.")
        );
        projectRepository.deleteProjectById(id);
    }
}
