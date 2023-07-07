package pilotproject.Project_Management_ELCA.service;

import pilotproject.Project_Management_ELCA.model.dto.ProjectDto;
import pilotproject.Project_Management_ELCA.model.entity.Project;

import java.util.List;

public interface ProjectService {
    List<Project> findAllProjects();

    Project findProjectByNumber(Integer number);

    void deleteSingleProject(Long id);

    ProjectDto addProject(ProjectDto dto);

    ProjectDto updateProject(ProjectDto dto);

    List<Project> searchProject(String searchText, String status);
}
