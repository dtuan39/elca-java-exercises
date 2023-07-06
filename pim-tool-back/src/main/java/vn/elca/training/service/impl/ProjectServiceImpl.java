package vn.elca.training.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QProject;
import vn.elca.training.model.exception.*;
import vn.elca.training.repository.EmployeeRepository;
import vn.elca.training.repository.GroupRepository;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;
import vn.elca.training.util.ApplicationMapper;
import vn.elca.training.validator.ProjectValidator;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ApplicationMapper mapper;

    @Autowired
    private ProjectValidator projectValidator;

    @Override
    public ProjectDto create(ProjectDto projectDto) throws StartDateAfterEndDateException,
            ProjectNumberAlreadyExistsException, InvalidProjectMembersException {
        // Validate the input data before creating the project
        projectValidator.validate(projectDto);

        // Convert DTO to Entity
        Project newProject = mapper.projectDtoToProject(projectDto);

        // Attach group to the project
        newProject.setGroup(groupRepository.findById(projectDto.getGroupId()).orElseThrow());

        // Attach employees to the project
        attachEmployeesToProject(newProject, projectDto.getMembers());

        try {// Save the project to the database
            Project savedProject = projectRepository.save(newProject);
            return mapper.projectToProjectDto(savedProject);
        } catch (DataIntegrityViolationException e) {
            throw new ProjectNumberAlreadyExistsException("Project number already exists");
        }
    }

    @Override
    public ProjectDto update(ProjectDto updateProjectDto) throws StartDateAfterEndDateException,
            ProjectNotFoundException, InvalidProjectMembersException {
        // Validate the input data before updating the project
        projectValidator.validate(updateProjectDto);

        // Retrieve the existing project entity from the database
        Project existingProject = projectRepository.findByProjectNumber(updateProjectDto.getProjectNumber())
                .orElseThrow(ProjectNotFoundException::new);

        // Concurrent update detection, using optimistic locking by Hibernate
        if (existingProject.getVersion() != updateProjectDto.getVersion()) {
            throw new ConcurrentUpdateException("Project has been updated by another user");
        }

        // Update the project with the new data
        existingProject.setName(updateProjectDto.getName());
        existingProject.setCustomer(updateProjectDto.getCustomer());
        existingProject.setStatus(Project.Status.valueOf(updateProjectDto.getStatus()));
        existingProject.setStartDate(updateProjectDto.getStartDate());
        existingProject.setEndDate(updateProjectDto.getEndDate());
        existingProject.setVersion(updateProjectDto.getVersion());

        // Attach group to the project
        existingProject.setGroup(groupRepository.findById(updateProjectDto.getGroupId())
                .orElseThrow(ProjectNotFoundException::new));

        // Attach employees to the project
        attachEmployeesToProject(existingProject, updateProjectDto.getMembers());

        // TODO : Hint : To handle concurrent update, the entity's status must not be persisted.
        return mapper.projectToProjectDto(projectRepository.save(existingProject));
    }

    @Override
    public Page<ProjectDto> findByKeyword(String keyword, String status, int page, int limit) {
        String[] parts = keyword.split("\\s+"); // Split the keyword into separate parts
        BooleanExpression conditions = null; // Initialize the conditions as null
        // Loop through the parts and build the conditions
        for (String part : parts) {
            if (conditions == null) {
                conditions = QProject.project.name.containsIgnoreCase(part)
                        .or(QProject.project.customer.containsIgnoreCase(part));
                try {
                    Integer projectNumber = Integer.parseInt(part);
                    conditions = conditions.or(QProject.project.projectNumber.eq(projectNumber));
                } catch (NumberFormatException e) {
                    // Ignore if the part cannot be parsed as an integer
                }
            } else {
                conditions = conditions.or(QProject.project.name.containsIgnoreCase(part))
                        .or(QProject.project.customer.containsIgnoreCase(part));
                try {
                    Integer projectNumber = Integer.parseInt(part);
                    conditions = conditions.or(QProject.project.projectNumber.eq(projectNumber));
                } catch (NumberFormatException e) {
                    // Ignore if the part cannot be parsed as an integer
                }
            }
        }
        //status string must be not null and is one of the values in Project.Status

        if (conditions != null && !StringUtils.isBlank(status) && Project.Status.contains(status)) {
            conditions = conditions.and(QProject.project.status.eq(Project.Status.valueOf(status)));
        }
        // Build sort request by project number
        Sort sort = Sort.by("projectNumber").ascending();
        // Build the pagination request
        Pageable pageable = PageRequest.of(page, limit, sort);
        // Retrieve the projects from the database
        return projectRepository.findAll(conditions, pageable).map(mapper::projectToProjectDto);
    }

    @Override
    public ProjectDto findById(long id) {
        return mapper.projectToProjectDto(projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new));
    }

    @Override
    public ProjectDto findProjectByNumber(Integer number) {
        return mapper.projectToProjectDto(projectRepository.findByProjectNumber(number).orElseThrow());
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public void deleteByProjectNumber(Integer projectNumber) throws ProjectNotInNewStatusException {
        Project project = projectRepository.findByProjectNumber(projectNumber).orElseThrow(ProjectNotFoundException::new);
        if (project.getStatus() != Project.Status.NEW) {
            //just delete the project if it is not in NEW status
            throw new ProjectNotInNewStatusException("Project is not in NEW status");
        }
        projectRepository.deleteByProjectNumber(projectNumber);
    }

    @Override
    public long count() {
        return projectRepository.count();
    }

    @Override
    public Page<ProjectDto> findAllOrderByProjectNumber(int page, int limit) {
        // Build the sorting request
        Sort sort = Sort.by("projectNumber").ascending();
        // Build the pagination and sorting request
        Pageable pageableAndSorting = PageRequest.of(page, limit, sort);
        // Retrieve the projects from the database
        Page<Project> projects = projectRepository.findAll(pageableAndSorting);
        return projects.map(mapper::projectToProjectDto);
    }

    private void attachEmployeesToProject(Project project, String members) {
        if (!StringUtils.isBlank(members)) {// if members is not empty
            List<String> visas = List.of(members.split(","));
            if (employeeRepository.existsByVisaIn(visas)) {
                project.setEmployees(new HashSet<>(employeeRepository.findByVisaIn(visas)));
            } else {//throw exception that project's members are not valid
                throw new InvalidProjectMembersException("Project's members are not valid.");
            }
        }
    }
}
