package vn.elca.training.service;

import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Project;

import java.util.List;

public interface ProjectService {

    String welcome();

    Project addProject(Project project);

    Project updateProject(Project project);

    void deleteProjectById(Long id);

    List<Project> findAllProject();

    List<Project> findProjectByProjectNumber(int id);

    List<Project> findProjectByAny(String value);

    boolean checkProjectNumber(int projectNumber);

    void updateProjectByProjectNumber(int projectNumber, Project project);
}


