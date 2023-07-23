package vn.elca.training.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.elca.training.model.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryCustom {
    Page<Project> findProjectByKeywordAndStatusSortByProjectNumber(String keyword, Project.Status status, Pageable pageable);
}
