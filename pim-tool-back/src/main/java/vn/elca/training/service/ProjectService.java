package vn.elca.training.service;

import org.springframework.data.domain.Page;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.exception.*;

/**
 * @author vlp
 */
public interface
ProjectService {
    ProjectDto create(ProjectDto projectDto)
            throws StartDateAfterEndDateException, ProjectNumberAlreadyExistsException, GroupNotFoundException;

    ProjectDto update(ProjectDto project)
            throws StartDateAfterEndDateException, ProjectNotFoundException, ConcurrentUpdateException, GroupNotFoundException;

    ProjectDto findById(long id) throws ProjectNotFoundException;

    void deleteById(long id) throws ProjectNotInNewStatusException, ProjectNotFoundException;

    Page<ProjectDto> findByKeyword(String keyword, ProjectDto.StatusDto status, int page, int limit);

    Page<ProjectDto> findAllOrderByProjectNumber(int page, int limit);
}
