package vn.elca.training.service;

import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.Task;
import vn.elca.training.model.exception.DeadlineAfterFinishingDateException;

import java.time.LocalDate;
import java.util.List;

/**
 * @author vlp
 */
public interface TaskService {
    List<Project> findProjectsByTaskName(String taskName);

    List<String> listNumberOfTasks(List<Project> projects);

    List<String> listProjectNameOfRecentTasks();

    List<String> listProjectNameOfRecentTasks2();

    List<Task> listTasksById(List<Long> ids);

    List<Task> listTasksById2(List<Long> ids);

    Task getTaskById(Long id);

    void updateDeadline(Long taskId, LocalDate deadline) throws DeadlineAfterFinishingDateException;

    void createTaskForProject(String taskName, LocalDate deadline, Project project);
}
