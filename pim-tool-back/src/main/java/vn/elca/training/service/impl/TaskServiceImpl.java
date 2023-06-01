/*
 * TaskService
 *
 * Project: KStA ZHQUEST
 *
 * Copyright 2014 by ELCA Informatik AG
 * Steinstrasse 21, CH-8036 Zurich
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of ELCA Informatik AG ("Confidential Information"). You
 * shall not disclose such "Confidential Information" and shall
 * use it only in accordance with the terms of the license
 * agreement you entered into with ELCA.
 */

package vn.elca.training.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.Task;
import vn.elca.training.model.entity.TaskAudit.AuditType;
import vn.elca.training.model.entity.TaskAudit.Status;
import vn.elca.training.model.exception.ApplicationUnexpectedException;
import vn.elca.training.model.exception.DeadlineAfterFinishingDateException;
import vn.elca.training.validator.TaskValidator;
import vn.elca.training.repository.TaskRepository;
import vn.elca.training.service.AuditService;
import vn.elca.training.service.TaskService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author vlp
 *
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {
	private Log logger = LogFactory.getLog(getClass());
	private static final int FETCH_LIMIT = 10;

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private AuditService auditService;
	@Autowired
	private TaskValidator taskValidator;

	@Override
	public List<Project> findProjectsByTaskName(String taskName) {
		return taskRepository.findProjectsByTaskName(taskName);
	}

	@Override
	public List<String> listNumberOfTasks(List<Project> projects) {
		List<String> result = new ArrayList<>(projects.size());
		for (Project project : projects) {
			result.add(String.format("Project %s has %s tasks.", project.getName(), project.getTasks().size()));
		}
		return result;
	}

	@Override
	public List<String> listProjectNameOfRecentTasks() {
		List<String> projectNames = new ArrayList<>(FETCH_LIMIT);
		List<Task> tasks = taskRepository.listRecentTasks(FETCH_LIMIT);
		for (Task task : tasks) {
			projectNames.add(task.getProject().getName());
		}
		return projectNames;
	}

	@Override
	public List<Task> listTasksById(List<Long> ids) {
		List<Task> tasks = new ArrayList<>(ids.size());
		for (Long id : ids) {
			tasks.add(getTaskById(id));
		}
		return tasks;
	}

	@Override
	public Task getTaskById(Long id) {
		return taskRepository.findById(id).orElse(null);
		// Should throw exception if not found
	}

	@Override
	public void updateDeadline(Long taskId, LocalDate deadline) throws DeadlineAfterFinishingDateException {
		Optional<Task> optional = taskRepository.findById(taskId);
		if (optional.isPresent()) {
			Task task = optional.get();
			task.setDeadline(deadline);
			save(task);
		}
		// Should throw exception if not found
	}

	@Override
	public void createTaskForProject(String taskName, LocalDate deadline, Project project) {
		Task task = new Task(project, taskName);
		task.setDeadline(deadline);
		AuditType auditType = AuditType.INSERT;
		try {
			task = save(task);
			auditService.saveAuditDataForTask(task, auditType, Status.SUCCESS, "Task was saved successfully.");
		} catch (Exception e) {
			String errorMessage = String.format("An exception (Error-ID = %s) happened when saving/updating task: %s",
					UUID.randomUUID().toString(), e.getMessage());
			logger.error(errorMessage, e);
			auditService.saveAuditDataForTask(task, auditType, Status.FAILED, errorMessage);

			throw new ApplicationUnexpectedException(e);
		}
	}

	private Task save(Task task) throws DeadlineAfterFinishingDateException {
		Task result = taskRepository.save(task);
		taskValidator.validate(task);

		return result;
	}
}