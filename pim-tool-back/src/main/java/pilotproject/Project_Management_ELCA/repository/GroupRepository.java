package pilotproject.Project_Management_ELCA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pilotproject.Project_Management_ELCA.model.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
