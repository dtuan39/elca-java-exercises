package vn.elca.training.service;

import org.springframework.data.domain.Page;
import vn.elca.training.model.dto.ProjectDTO;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.exception.ProjectInfoException;

import java.util.List;

public interface ProjectService {
    List<Project> findAll();

    List<Project> getProjectsByOrderOfProjectNumber();

    Page<Project> getProjectWithPagination(int offset);

    Page<Project> searchProjectWithPagination(String searchValue, String status, int offset);

    ProjectDTO getProjectByProjectNumber(Integer projectNumber);


    Project addProject(ProjectDTO dto) throws ProjectInfoException;

    void deleteProjects(List<Integer> projectNumbers);


    Project updateProject(ProjectDTO dto) throws ProjectInfoException;
}
