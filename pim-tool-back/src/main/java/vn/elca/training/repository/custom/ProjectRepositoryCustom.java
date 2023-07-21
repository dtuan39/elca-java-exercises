package vn.elca.training.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import vn.elca.training.model.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryCustom {
    List<Project> findProjectByConditions(BooleanExpression predicate, int page, int limit);

    Optional<Project> findProjectByNumberLoadMembers(Integer number);
}
