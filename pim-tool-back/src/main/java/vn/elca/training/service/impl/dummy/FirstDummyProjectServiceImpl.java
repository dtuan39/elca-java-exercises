package vn.elca.training.service.impl.dummy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import vn.elca.training.exception.ProjectNotFoundException;
import vn.elca.training.exception.UpdateProjectException;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author gtn
 *
 */
@Component
@Profile("dummy")
public class FirstDummyProjectServiceImpl extends AbstractDummyProjectService implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'count'");
    }

    @Override
    public Project findById(long id) throws ProjectNotFoundException {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with ID: " + id));
    }

    @Override
    public Project update(ProjectDto projectDto) throws ProjectNotFoundException {
        // Find current project by id in database
        Project found = findById(projectDto.getId());

        // Apply changes
        found.setName(projectDto.getName());
        found.setFinishingDate(projectDto.getFinishingDate());

        // Save the updated project
        Project response = projectRepository.save(found);
        if(response == null){
            throw new UpdateProjectException("Failed to update project");
        }
        return response;
    }

    @Override
    public List<Project> findByKeyword(String keyword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByKeyword'");
    }

    @Override
    public void createMaintenanceProject(long oldProjectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createMaintenanceProject'");
    }

}
