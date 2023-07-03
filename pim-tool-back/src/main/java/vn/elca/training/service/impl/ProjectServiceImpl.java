package vn.elca.training.service.impl;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QProject;
import vn.elca.training.model.exception.UserNotFoundException;
import vn.elca.training.service.ProjectService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @PersistenceContext
    private EntityManager em;


    @Override
    public String welcome() {
        return "Welcome to ELCA Bootcamp";
    }

    @Override
    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> findAllProject() {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .fetch();
    }

    @Override
    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public void deleteProjectById(Long id) {
        projectRepository.deleteProjectById(id);
    }

    @Override
    public Project findProjectByid(Long id) {
        return projectRepository.findProjectById(id).orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
    }

}
