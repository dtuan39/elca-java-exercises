package vn.elca.training.service;

import java.util.List;

import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;

/**
 * @author vlp
 *
 */
public interface ProjectService {
    List<Project> findAll();

    Project findById(long id);

    Project update(ProjectDto project);
    
    long count();

    List<Project> findByKeyword(String keyword);
}
