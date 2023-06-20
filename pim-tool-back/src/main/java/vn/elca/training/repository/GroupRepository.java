package vn.elca.training.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.elca.training.model.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}