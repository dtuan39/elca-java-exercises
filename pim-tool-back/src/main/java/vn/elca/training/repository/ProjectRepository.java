package vn.elca.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import vn.elca.training.model.entity.Project;
import vn.elca.training.repository.custom.ProjectRepositoryCustom;

import java.util.Optional;

/**
 * @author vlp
 * @author thomas.dang
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>,
        QuerydslPredicateExecutor<Project>, ProjectRepositoryCustom {
    Optional<Project> findByProjectNumber(Integer projectNumber);

    void deleteByProjectNumber(Integer projectNumber);
}
