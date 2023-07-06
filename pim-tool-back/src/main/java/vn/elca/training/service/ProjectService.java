package vn.elca.training.service;

import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Project;

import java.util.List;

public interface ProjectService {

    String welcome();

    Project addProject(Project project);

    List<Project> findAllProject();

    Project updateProject(Project project);

    @Transactional
    void deleteProjectById(Long id);

    List<Project> findProjectByProjectNumber(int id);

    List<Project> findProjectByAny(String value);

    boolean checkProjectNumber(int projectNumber);
}


