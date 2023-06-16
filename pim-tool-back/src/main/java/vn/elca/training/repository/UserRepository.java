package vn.elca.training.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import vn.elca.training.model.entity.User;

/**
 * @author gtn
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    @EntityGraph(attributePaths = {"tasks", "tasks.project"})
    User findUserByUsername(String username);
}
