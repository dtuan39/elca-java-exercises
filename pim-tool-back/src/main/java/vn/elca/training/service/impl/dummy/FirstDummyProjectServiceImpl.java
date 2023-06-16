package vn.elca.training.service.impl.dummy;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

/**
 * @author gtn
 */
@Component
@Profile("dummy")
@Primary
public class FirstDummyProjectServiceImpl extends AbstractDummyProjectService implements ProjectService {
    private final ProjectRepository projectRepository;


    public FirstDummyProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public long count() {
        return projectRepository.count();
    }

    public List<Project> findByNameContaining(String substringName) {
        return projectRepository.findByNameContaining(substringName);
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find the project"));
    }

    @Transactional
    @Override
    public void updateProject(Long id, String name, LocalDate date) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        project.setName(name);
        project.setFinishingDate(date);
    }
}
