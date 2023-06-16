package vn.elca.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import vn.elca.training.model.entity.Group;


public interface GroupRepository extends JpaRepository<Group, Integer>, QuerydslPredicateExecutor<Group> {
    Group findGroupByLeaderId(int id);
}
