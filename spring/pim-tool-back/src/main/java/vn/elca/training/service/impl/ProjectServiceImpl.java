package vn.elca.training.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.enumaration.Status;
import vn.elca.training.model.dto.ProjectDTO;
import vn.elca.training.model.entity.*;
import vn.elca.training.model.exception.ProjectInfoException;
import vn.elca.training.repository.EmployeeRepository;
import vn.elca.training.repository.GroupRepository;
import vn.elca.training.repository.ProjectEmployeeRepository;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.ApplicationMapper;

import javax.el.Expression;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;


@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepo;
    private final GroupRepository groupRepo;
    private final ProjectEmployeeRepository projectEmployeeRepo;
    private final ApplicationMapper mapper;
    private final EmployeeRepository employeeRepo;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepo, GroupRepository groupRepo,
                              ProjectEmployeeRepository projectEmployeeRepo, ApplicationMapper mapper,
                              EmployeeRepository employeeRepo) {
        this.projectRepo = projectRepo;
        this.groupRepo = groupRepo;
        this.projectEmployeeRepo = projectEmployeeRepo;
        this.mapper = mapper;
        this.employeeRepo = employeeRepo;
    }

    @Override
    public List<Project> findAll() {
        return projectRepo.findAll();
    }

    @Override
    public List<Project> getProjectsByOrderOfProjectNumber() {
        return this.projectRepo.findAll(Sort.by(Sort.Direction.ASC, "projectNumber"));
    }

    @Override
    public Page<Project> getProjectWithPagination(int offset) {
        int pageSize = 5;
        PageRequest pageReq = PageRequest.of(offset, pageSize, Sort.by(Sort.Direction.ASC, "projectNumber"));
        return this.projectRepo.findAll(pageReq);
    }

    @Override
    public Page<Project> searchProjectWithPagination(String searchValue, String statusValue, int offset) {
        BooleanExpression whereClause = null;

        if (searchValue != null && !searchValue.isEmpty()) {
            whereClause = QProject.project.name.contains(searchValue)
                    .or(QProject.project.customer.contains(searchValue))
                    .or(QProject.project.projectNumber.stringValue().contains(searchValue));
        }

        if (statusValue != null && !statusValue.isEmpty()) {
            if (whereClause == null) {
                whereClause = QProject.project.status.stringValue().contains(statusValue);
            } else {
                whereClause = whereClause.and(QProject.project.status.stringValue().contains(statusValue));
            }
        }

        List<Project> result = new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(whereClause)
                .orderBy(QProject.project.projectNumber.asc())
                .fetch();


        int pageSize = 5;
        int start = offset * pageSize;
        int end = Math.min(start + pageSize, result.size());

        List<Project> content = result.subList(start, end);


        return new PageImpl<>(content, PageRequest.of(offset, pageSize), result.size());
    }


    @Override
    @Transactional
    public ProjectDTO getProjectByProjectNumber(Integer projectNumber) {
        Project project = projectRepo.findByProjectNumber(projectNumber);
        if (project == null) {
            throw new EntityNotFoundException("Project not found at getProjectByProjectNumber function ");
        }
        ProjectDTO dto = mapper.projectToProjectDto(project);
        List<Employee> employeeList = new JPAQuery<>(em)
                .select(QEmployee.employee)
                .from(QEmployee.employee)
                .innerJoin(QEmployee.employee.projectEmployees, QProjectEmployee.projectEmployee)
                .where(QProjectEmployee.projectEmployee.project.id.eq(project.getId()))
                .fetch();

        dto.setEmployees(employeeList);
        return dto;
    }

    @Override
    @Transactional
    public Project addProject(ProjectDTO dto) throws ProjectInfoException {
        ProjectInfoException projectException = new ProjectInfoException();

        //check project is existed
        Project newProject = this.projectRepo.findByProjectNumber(dto.getProjectNumber());
        if (newProject != null) {
            projectException.addErrors("Project Number", "Project Number is dupplicated");
        }
        //check group is existed
        Group group = groupRepo.findById(dto.getGroupId()).orElse(null);
        if (group == null) {
            projectException.addErrors("Group ID", "Group ID is not existed");
        }
        //check end date must after startDate
        if (dto.getEndDate() != null) {
            if (dto.getStartDate().isAfter(dto.getEndDate())) {
                projectException.addErrors("End Date", "End Date must be after Start Date");
            }
        }
        if (projectException.getErrorsMap().size() > 0) {
            throw projectException;
        }

        newProject = new Project();
        newProject.setGroupId(groupRepo.findById(dto.getGroupId()).get());
        newProject.setName(dto.getName());
        newProject.setProjectNumber(dto.getProjectNumber());
        newProject.setCustomer(dto.getCustomer());
        newProject.setStatus(Status.valueOf(dto.getStatus()));
        newProject.setStartDate(dto.getStartDate());
        newProject.setEndDate(dto.getEndDate());

        newProject = this.projectRepo.save(newProject);

        for (Employee e : dto.getEmployees()) {
            Employee newEmployee = this.employeeRepo.findById(e.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Employees not found!"));

            ProjectEmployee newProjectEmployee = new ProjectEmployee();
            newProjectEmployee.setProject(newProject);
            newProjectEmployee.setEmployee(newEmployee);

            projectEmployeeRepo.save(newProjectEmployee);
        }


        return this.projectRepo.save(newProject);
    }

    @Override
    @Transactional
    public Project updateProject(ProjectDTO dto) throws ProjectInfoException {
        ProjectInfoException projectException = new ProjectInfoException();

        //check project is existed
        Project newProject = this.projectRepo.findByProjectNumber(dto.getProjectNumber());
        if (newProject == null) {
            projectException.addErrors("Project Number", "Project Number is not found!");
        }
        //check group is existed
        Group group = groupRepo.findById(dto.getGroupId()).orElse(null);
        if (group == null) {
            projectException.addErrors("Group ID", "Group ID is not existed");
        }
        //check end date must after startDate
        if (dto.getEndDate() != null) {
            if (dto.getStartDate().isAfter(dto.getEndDate())) {
                projectException.addErrors("End Date", "End Date must be after Start Date");
            }
        }
        if (projectException.getErrorsMap().size() > 0) {
            throw projectException;
        }

        newProject.setGroupId(groupRepo.findById(dto.getGroupId()).get());
        newProject.setName(dto.getName());
        newProject.setProjectNumber(dto.getProjectNumber());
        newProject.setCustomer(dto.getCustomer());
        newProject.setStatus(Status.valueOf(dto.getStatus()));
        newProject.setStartDate(dto.getStartDate());
        newProject.setEndDate(dto.getEndDate());

        this.deleteProjectEmployee(newProject.getId());


        for (Employee e : dto.getEmployees()) {
            Employee newEmployee = this.employeeRepo.findById(e.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Employees not found!"));

            ProjectEmployee newProjectEmployee = new ProjectEmployee();
            newProjectEmployee.setProject(newProject);
            newProjectEmployee.setEmployee(newEmployee);

            projectEmployeeRepo.save(newProjectEmployee);
        }

        return this.projectRepo.save(newProject);
    }

    @Override
    @Transactional
    public void deleteProjects(List<Integer> projectNumbers) {
        for (Integer number : projectNumbers) {
            Project project = this.projectRepo.findByProjectNumber(number);
            List<ProjectEmployee> projectEmployeeList = this.projectEmployeeRepo.findAllByProjectId(project.getId());
            for (ProjectEmployee pe : projectEmployeeList) {
                this.projectEmployeeRepo.delete(pe);
            }
            this.projectRepo.delete(project);
        }
    }

    @Transactional
    public void deleteProjectEmployee(Long projectId) {
        System.out.println("Project ID:" + projectId);
        List<ProjectEmployee> list = projectEmployeeRepo.findAllByProjectId(projectId);
        for (ProjectEmployee pe : list) {
            this.projectEmployeeRepo.delete(pe);
        }
        System.out.println("Delete success");
    }


}
