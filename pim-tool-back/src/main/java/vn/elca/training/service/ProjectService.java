package vn.elca.training.service;

import java.util.List;

import org.springframework.data.domain.Page;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.exception.ProjectNotInNewStatusException;
import vn.elca.training.model.exception.ProjectNumberAlreadyExistsException;
import vn.elca.training.model.exception.StartDateAfterEndDateException;

/**
 * @author vlp
 */
public interface
ProjectService {
    ProjectDto create(ProjectDto projectDto) throws StartDateAfterEndDateException, ProjectNumberAlreadyExistsException;

    ProjectDto update(ProjectDto project) throws StartDateAfterEndDateException;

    ProjectDto findById(long id);

    boolean delete(long id);

    ProjectDto findProjectByNumber(Integer number);

    void deleteByProjectNumber(Integer projectNumber) throws ProjectNotInNewStatusException;

    long count();

    Page<ProjectDto> findByKeyword(String keyword, String status, int page, int limit);

    Page<ProjectDto> findAllOrderByProjectNumber(int page, int limit);
}
