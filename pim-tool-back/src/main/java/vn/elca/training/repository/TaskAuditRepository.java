package vn.elca.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.elca.training.model.entity.TaskAudit;

/**
 * @author vlp
 *
 */
@Repository
public interface TaskAuditRepository extends JpaRepository<TaskAudit, Long> {}
