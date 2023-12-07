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
    @Transactional(rollbackFor = {ObjectOptimisticLockingFailureException.class, Exception.class})
    public Project updateProject(ProjectDto dto) {
        try {
            Project entity = projectRepository.findProjectByNumber(dto.getNumber());

            entityManager.detach(entity);

            entity.setCustomer(dto.getCustomer());
            entity.setName(dto.getName());
            entity.setStatus(Project.Status.valueOf(dto.getStatus().toString()));
            entity.setStartDate(dto.getStartDate());
            entity.setEndDate(dto.getEndDate());
            entity.setGroup(groupRepository.findById(dto.getGroupId()).orElseThrow());
            entity.setVersion(dto.getVersion());

            return projectRepository.save(entity);
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
    @Transactional
    public void deleteSingleProject(Long id) {
        projectRepository.deleteProjectById(id);
    }
}
