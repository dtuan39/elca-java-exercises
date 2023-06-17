package vn.elca.training.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import vn.elca.training.model.entity.User;

/**
 * @author gtn
 *
 */
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    @EntityGraph(attributePaths = {"tasks", "tasks.project"})
    User findUserByUsername(String username);
}
