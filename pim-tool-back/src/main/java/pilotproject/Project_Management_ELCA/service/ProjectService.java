package pilotproject.Project_Management_ELCA.service;

import pilotproject.Project_Management_ELCA.model.dto.ProjectDto;
import pilotproject.Project_Management_ELCA.model.dto.ProjectMembersDto;
import pilotproject.Project_Management_ELCA.model.entity.Project;

import java.util.Collection;
import java.util.List;

public interface ProjectService {
    List<Project> findAllProjects();

    Project findProjectByNumber(Integer number);

    int[] findProjectMembersByNumber(Integer number);

    void deleteSingleProject(Long id);

    Project addProject(ProjectDto dto);

    ProjectMembersDto updateProject(ProjectMembersDto dto);

    List<Project> searchProject(String searchText, String status);

    Project createProject(ProjectMembersDto projectMembersDto);

    Long countProjects();

    List<Project> getProjectsPagination(int limit, int skip);

    List<Project> searchProjectPagination(String searchText, String status, int limit, int skip);
}
