package vn.elca.training.repository.projectcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.UserNotFoundException;

import java.util.List;

@Service
public class ProjectRepositoryImpl {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectRepositoryImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> findAllProject() {
        return projectRepository.findAll();
    }

    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProjectById(Long id) {
        projectRepository.deleteProjectById(id);
    }

    public Project findProjectByid(Long id) {
        return projectRepository.findProjectById(id).orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

}
