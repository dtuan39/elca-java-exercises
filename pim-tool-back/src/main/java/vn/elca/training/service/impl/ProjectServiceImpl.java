package vn.elca.training.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author vlp
 */
@Service
@Profile("!dummy | dev")
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository theProjectRepository) {
        this.projectRepository = theProjectRepository;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public long count() {
        return projectRepository.count();
    }

    @Override
    public List<Project> findByNameContaining(String substringName) {
        return null;
    }

    @Override
    public Project getProjectById(Long id) {
        return null;
    }

    @Override
    public void updateProject(Long id, String name, LocalDate date) {
    }

}
