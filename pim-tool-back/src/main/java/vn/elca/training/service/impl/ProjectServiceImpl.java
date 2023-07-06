package vn.elca.training.service.impl;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QProject;
import vn.elca.training.model.exception.ProjectNumberAlreadyExistsException;
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
        if (checkProjectNumber(project.getProjectNumber())) {
            throw new ProjectNumberAlreadyExistsException("The project number already existed. Please select a different number");
        } else {
            return projectRepository.save(project);
        }
    }

    @Override
    public List<Project> findAllProject() {
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .orderBy(QProject.project.projectNumber.asc())
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
    public List<Project> findProjectByProjectNumber(int projectNumber) {
//        return projectRepository.findProjectById(id).orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found"));
        return new JPAQuery<Project>(em)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(projectNumber))
                .fetch();
    }

    @Override
    public List<Project> findProjectByAny(String value) {
//    SELECT * FROM PROJECT WHERE name LIKE '%t%' OR PROJECT_NUMBER LIKE '%2%' or CUSTOMER LIKE '%t%'
        return new JPAQuery<Project>(em)
                .select(QProject.project)
                .from(QProject.project)
                .where(QProject.project.projectNumber.like("%" + value + "%")
                        .or(QProject.project.name.like("%" + value + "%"))
                        .or(QProject.project.customer.like("%" + value + "%"))
                        .or(QProject.project.status.eq(value)))
                .fetch();
    }

    @Override
    public boolean checkProjectNumber(int projectNumber) {
        return new JPAQuery<Project>(em)
                .select(QProject.project)
                .from(QProject.project)
                .where(QProject.project.projectNumber.eq(projectNumber))
                .fetchFirst()!= null;
    }

}
