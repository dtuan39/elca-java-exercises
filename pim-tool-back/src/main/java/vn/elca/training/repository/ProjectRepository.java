package vn.elca.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.custom.ProjectRepositoryCustom;

import java.util.List;

/**
 * @author vlp
 * @author thomas.dang
 */
public interface ProjectRepository extends JpaRepository<Project, Long>,
        QuerydslPredicateExecutor<Project>, ProjectRepositoryCustom {

    void deleteAllByIdIn(List<Long> ids);

    List<Project> findAllByIdIn(List<Long> ids);

}
