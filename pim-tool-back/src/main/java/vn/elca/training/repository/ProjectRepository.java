package vn.elca.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import vn.elca.training.model.entity.Project;

/**
 * @author vlp
 *
 */
public interface ProjectRepository extends JpaRepository<Project, Long>, QuerydslPredicateExecutor<Project> {
    List<Project> findByNameContainingIgnoreCase(String keyword);
    Project findByName(String name);
}
