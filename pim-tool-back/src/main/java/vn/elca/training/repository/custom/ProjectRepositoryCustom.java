package vn.elca.training.repository.custom;

import vn.elca.training.model.entity.Project;

import java.util.List;

public interface ProjectRepositoryCustom {
    List<Project> findAllProject();

    List<Project> findProjectByProjectNumber(int projectNumber);

    List<Project> findProjectByAny(String value);

    boolean checkProjectNumber(int projectNumber);

    void updateProjectByProjectNumber(int projectNumber, Project project);
}
