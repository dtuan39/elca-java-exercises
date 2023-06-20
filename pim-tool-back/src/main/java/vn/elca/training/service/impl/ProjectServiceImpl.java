package vn.elca.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.elca.training.exception.UpdateProjectException;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author vlp
 * 
 */
@Service
@Profile("!dummy | dev")
@Primary
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public long count() {
        return projectRepository.count();
    }

    @Override
    public Project findById(long id) {
        return projectRepository.findById(id).orElseThrow();
    }

    @Override
    public Project update(ProjectDto projectDto) {
        // Find current project by id in database
        Project found = findById(projectDto.getId());

        // Apply changes
        found.setName(projectDto.getName());
        found.setFinishingDate(projectDto.getFinishingDate());

        // Save the updated project
        Project response = projectRepository.save(found);
        if (response == null) {
            throw new UpdateProjectException("Failed to update project");
        }
        return response;
    }

    @Override
    public List<Project> findByKeyword(String keyword) {
        return projectRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    @Transactional
    public void createMaintenanceProject(long oldProjectId) {
        // Retrieve the old project from the repository
        Project oldProject = projectRepository.findById(oldProjectId)
                .orElseThrow(RuntimeException::new);

        // Construct the name of the new project
        String newProjectName = oldProject.getName() + " Maint. " + LocalDate.now().getYear();

        // Create the maintenance project
        Project maintenanceProject = new Project();
        maintenanceProject.setName(newProjectName);
        maintenanceProject.setActivated(false);

        // Save the maintenance project
        projectRepository.save(maintenanceProject);

        // Update the old project to be inactive
        oldProject.setActivated(false);
        projectRepository.save(oldProject);

        // Simulate an exception to test rollback behavior (optional)
        throw new RuntimeException("Simulated exception");
    }

}
