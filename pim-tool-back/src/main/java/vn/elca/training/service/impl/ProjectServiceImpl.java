package vn.elca.training.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QProject;
import vn.elca.training.model.exception.*;
import vn.elca.training.model.mapping.ProjectMapper;
import vn.elca.training.repository.EmployeeRepository;
import vn.elca.training.repository.GroupRepository;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;
import vn.elca.training.validator.ProjectValidator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final GroupRepository groupRepository;
    private final ProjectMapper mapper;
    private final ProjectValidator validator;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, EmployeeRepository employeeRepository, GroupRepository groupRepository, ProjectMapper mapper, ProjectValidator validator) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.groupRepository = groupRepository;
        this.mapper = mapper;
        this.validator = validator;
    }


    @Override
    @Transactional(rollbackFor = {DataIntegrityViolationException.class, Exception.class})
    public ProjectDto create(ProjectDto projectDto)
            throws StartDateAfterEndDateException, ProjectNumberAlreadyExistsException, GroupNotFoundException {
        validator.validate(projectDto);

        Project newProject = mapper.toEntity(projectDto);

        // Project's group and members must set manually because the mapper cannot access the database
        newProject.setGroup(groupRepository.findById(projectDto.getGroupId())
                .orElseThrow(() -> new GroupNotFoundException(projectDto.getGroupId())));
        newProject.setEmployees(employeeRepository.findByVisaIn(List.of(projectDto.getMembers().split(","))));

        try {
            Project savedProject = projectRepository.save(newProject);
            return mapper.toDTO(savedProject);
        } catch (DataIntegrityViolationException e) {
            throw new ProjectNumberAlreadyExistsException(String.format("Project number %d already exists",
                    projectDto.getProjectNumber()));
        }
    }

    @Override
    @Transactional(rollbackFor = {ObjectOptimisticLockingFailureException.class, Exception.class})
    public ProjectDto update(ProjectDto projectDto)
            throws StartDateAfterEndDateException, ProjectNotFoundException, GroupNotFoundException,
            ConcurrentUpdateException {
        validator.validate(projectDto);

        Project existingProject = projectRepository.findById(projectDto.getId())
                .orElseThrow(() -> new ProjectNotFoundException(projectDto.getId()));

        // Hibernate will automatically detect concurrent update and throw an exception
        em.detach(existingProject);

        // Update the project with the new data
        existingProject.setName(projectDto.getName());
        existingProject.setCustomer(projectDto.getCustomer());
        existingProject.setStatus(Project.Status.valueOf(projectDto.getStatus().toString()));
        existingProject.setStartDate(projectDto.getStartDate());
        existingProject.setEndDate(projectDto.getEndDate());
        existingProject.setVersion(projectDto.getVersion());

        // Project's group and members must set manually because the mapper cannot access the database
        existingProject.setGroup(groupRepository.findById(projectDto.getGroupId())
                .orElseThrow(GroupNotFoundException::new));
        existingProject.setEmployees(employeeRepository.findByVisaIn(
                List.of(projectDto.getMembers().split(","))));

        try {
            Project response = projectRepository.save(existingProject);
            return mapper.toDTO(response);
        } catch (ObjectOptimisticLockingFailureException e) { // Concurrent update exception with optimistic locking
            throw new ConcurrentUpdateException(
                    String.format("Project with project number %d has been updated by another user",
                            projectDto.getProjectNumber()));
        }
    }

    @Override
    public Page<ProjectDto> findByKeyword(String keyword, ProjectDto.StatusDto status, int page, int limit) {
        // TODO : Optimize the search query
        String[] parts = keyword.split("\\s+"); // Split the keyword into separate parts
        BooleanExpression conditions = null; // Initialize the conditions as null
        // Loop through the parts and build the conditions
        for (String part : parts) {
            if (conditions == null) {
                conditions = QProject.project.name.containsIgnoreCase(part).or(QProject.project.customer.containsIgnoreCase(part));
                try {
                    Integer projectNumber = Integer.parseInt(part);
                    conditions = conditions.or(QProject.project.projectNumber.eq(projectNumber));
                } catch (NumberFormatException e) {
                    // Ignore if the part cannot be parsed as an integer
                }
            } else {
                conditions = conditions.or(QProject.project.name.containsIgnoreCase(part)).or(QProject.project.customer.containsIgnoreCase(part));
                try {
                    Integer projectNumber = Integer.parseInt(part);
                    conditions = conditions.or(QProject.project.projectNumber.eq(projectNumber));
                } catch (NumberFormatException e) {
                    // Ignore if the part cannot be parsed as an integer
                }
            }
        }
        //status string must be not null and is one of the values in Project.Status

        if (conditions != null && status != null) {
            conditions = conditions.and(QProject.project.status.eq(Project.Status.valueOf(status.toString())));
        }
        // Build sort request by project number
        Sort sort = Sort.by("projectNumber").ascending();
        // Build the pagination request
        Pageable pageable = PageRequest.of(page, limit, sort);
        // Retrieve the projects from the database
        if (conditions != null) return projectRepository.findAll(conditions, pageable).map(mapper::toDTO);
        else return projectRepository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public ProjectDto findById(long id) throws ProjectNotFoundException {
        return mapper.toDTO(projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id)));
    }

    @Override
    public void deleteById(long id) throws ProjectNotInNewStatusException, ProjectNotFoundException {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
        if (project.getStatus() != Project.Status.NEW) {
            throw new ProjectNotInNewStatusException(String.format("Project with project number %d is not in NEW status",
                    project.getProjectNumber()));
        }
        projectRepository.deleteById(id);
    }

    @Override
    public Page<ProjectDto> findAllOrderByProjectNumber(int page, int limit) {
        // Build the sorting request
        Sort sort = Sort.by("projectNumber").ascending();
        // Build the pagination and sorting request
        Pageable pageableAndSorting = PageRequest.of(page, limit, sort);
        // Retrieve the projects from the database
        Page<Project> projects = projectRepository.findAll(pageableAndSorting);
        return projects.map(mapper::toDTO);
    }
}