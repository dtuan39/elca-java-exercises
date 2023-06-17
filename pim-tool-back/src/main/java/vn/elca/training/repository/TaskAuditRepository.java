package vn.elca.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.elca.training.model.entity.TaskAudit;

/**
 * @author vlp
 *
 */
public interface TaskAuditRepository extends JpaRepository<TaskAudit, Long> {}
