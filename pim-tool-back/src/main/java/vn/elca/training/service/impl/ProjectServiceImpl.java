package vn.elca.training.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.service.ProjectService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository projectRepository;
    @PersistenceContext
    private EntityManager em;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public String welcome() {
        return "Welcome to ELCA Bootcamp";
    }

    @Override
    public Project addProject(Project project) {
        if (projectRepository.checkProjectNumber(project.getProjectNumber())) {
            throw new ProjectNumberAlreadyExistsException("The project number already existed. Please select a different number");
        }
        return projectRepository.save(project);
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
    public List<Project> findProjectByProjectNumber(int projectNumber) {
//        return projectRepository.findProjectById(id).orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
        return projectRepository.findProjectByProjectNumber(projectNumber);
    }

    @Override
    public List<Project> findProjectByAny(String value) {
//    SELECT * FROM PROJECT WHERE name LIKE '%t%' OR PROJECT_NUMBER LIKE '%2%' or CUSTOMER LIKE '%t%'
        return projectRepository.findProjectByAny(value);
    }

    @Override
    public boolean checkProjectNumber(int projectNumber) {
        return projectRepository.checkProjectNumber(projectNumber);
    }

    @Override
    public List<Project> findAllProject() {
        return projectRepository.findAllProject();
    }

    @Override
    public void updateProjectByProjectNumber(int projectNumber, Project project){
        projectRepository.updateProjectByProjectNumber(projectNumber, project);
    }

}
