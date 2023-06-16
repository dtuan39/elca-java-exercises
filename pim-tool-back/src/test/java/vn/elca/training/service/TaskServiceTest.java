package vn.elca.training.service;

import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Hibernate;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.ApplicationWebConfig;
import vn.elca.training.model.entity.*;
import vn.elca.training.model.exception.DeadlineAfterFinishingDateException;
import vn.elca.training.repository.ProjectRepository;
import vn.elca.training.repository.TaskRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vlp
 */
@ContextConfiguration(classes = {ApplicationWebConfig.class})
@RunWith(value = SpringRunner.class)
// please remove this annotation to do the Hibernate exercise
//@Ignore
public class TaskServiceTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskService taskService;

    private void createProjectAndTaskData(int nbProjects, int nbTasks) {
        // create 'nbProjects' Projects, each project has 'nbTasks' tasks.
        for (int i = 1; i <= nbProjects; i++) {
            LocalDate finishingDate = LocalDate.now().plusYears(1);
            Project project = new Project(String.format("Project %s", i), finishingDate);
            project = projectRepository.save(project);
            for (int j = 0; j < nbTasks; j++) {
                taskRepository.save(new Task(project, String.format("Task %s", j)));
            }
        }
    }

    @Test
    @Transactional
    public void testListNumberOfTasks() {
        createProjectAndTaskData(1, 3);
        List<Project> projectsByTaskName = taskService.findProjectsByTaskName("Task 1");
        Assert.assertTrue(taskService.listNumberOfTasks(projectsByTaskName).size() > 0);
        /*
        Error: failed to lazily initialize a collection of role: vn.elca.training.model.entity.Project.tasks
        Solution: Change FetchType in Project  or add @Transactional
        Explanation:
            Solution 1:
            Property tasks in Project class has FetchType=Lazy
            -> When project is loaded from database, tasks haven't loaded yet -> error
            Solution 2:
            taskService.listNumberOfTasks(projectsByTaskName)
            -> access tasks property inside Project Class
            but tasks has FetchType LAZY
            -> So we need add @Transactional to create transactional context
            -> all operations executed within same context until transaction finish
        */
    }

    @Test
    public void testShowProjectNameOfTopTenNewTasks() {
        createProjectAndTaskData(100, 1);
        System.out.println(">>>>>>> Start Test case >>>>>");
        List<String> names = taskService.listProjectNameOfRecentTasks(); //size = 10
        Assert.assertTrue(names.size() > 0);
    }

    @Test
    public void testShowProjectNameOfTopTenNewTasks2() {
        createProjectAndTaskData(100, 1);
        System.out.println(">>>>>>> Start Test case >>>>>");
        List<String> names = taskService.listProjectNameOfRecentTasks2();
        Assert.assertTrue(names.size() > 0);
        //each query statement printed out by Hibernate logger is by calling task.getProject()
        //inside for each loop, it will execute 10 times -> 10 lines of logging
        //solution : joining table in query statement -> 1 select statement
    }

    @Test
    public void testListTasksByIds() {
        createProjectAndTaskData(100, 1);
        int size = 10;
        List<Long> ids = new ArrayList<>(size);
        for (long i = 0; i < size; i++) {
            ids.add(i);
        }
        System.out.println(">>>>>>> Start Test case >>>>>");
        List<Task> tasks = taskService.listTasksById(ids);
        Assert.assertTrue(tasks.size() > 0);
    }

    @Test
    public void testListTasksByIds2() {
        //problem SELECT N+1 by calling getTaskById() multiple time in loop
        //solution: querying with joined table
        createProjectAndTaskData(100, 1);
        int size = 10;
        List<Long> ids = new ArrayList<>(size);
        for (long i = 0; i < size; i++) {
            ids.add(i);
        }
        System.out.println(">>>>>>> Start Test case >>>>>");
        List<Task> tasks = taskService.listTasksById2(ids);
        Assert.assertTrue(tasks.size() > 0);
    }

    @Test
    public void testUpdateDeadline() {
        createProjectAndTaskData(1, 5);
        final Long taskId = 5L;
        final Task task = taskRepository.findById(taskId).orElse(null);
        Assert.assertNotNull(task);
        final LocalDate finishingDate = task.getDeadline(); //2023-06-15
        try {
            // test update the deadline with a new invalid deadline
            LocalDate newDeadline = finishingDate.plusYears(2);
            taskService.updateDeadline(taskId, newDeadline);
            //this function will throw an exception
            // so we need to add @Transactional to roll back at the time the Exception is thrown
            //Note: this exception is a checked exception
            //so we need to add rollBackFor for the @Transactional
        } catch (DeadlineAfterFinishingDateException e) {
            em.clear(); //move all instances from managed state to detached state
            Task task1 = taskRepository.findById(taskId).orElse(null);
            Assert.assertNotNull(task1);
            Assert.assertEquals("Deadline should not be changed here", finishingDate, task1.getDeadline());
        }
    }

    @Test
    public void testCreateTaskForProject() {
        try {
            LocalDate curDate = LocalDate.now();
            LocalDate dateBefore = curDate.minusDays(1);
            Project project = projectRepository.saveAndFlush(new Project("Project 1", dateBefore));
            taskService.createTaskForProject("Task test CreateTaskFromProject", curDate, project);
        } catch (Exception e) {
            // just swallow it here because we are testing the case the task is inserted with invalid deadline.
        }
        Assert.assertNull("Task should not be saved to DB because its deadline is invalid.",
                new JPAQuery<Task>(em)
                        .from(QTask.task)
                        .where(QTask.task.name.eq("Task test CreateTaskFromProject"))
                        .fetchFirst());

        Assert.assertNotNull("Task audit data should be saved into DB for admin to trace back later.",
                new JPAQuery<Task>(em)
                        .from(QTaskAudit.taskAudit)
                        .where(QTaskAudit.taskAudit.taskName.eq("Task test CreateTaskFromProject")
                                .and(QTaskAudit.taskAudit.auditType.eq(TaskAudit.AuditType.INSERT)))
                        .fetchFirst());
        //the method of auditService have to be executed
        //but TaskService, who will call auditService is defined with @Transactional
        //and that method will throw an exception -> rolled back on auditService method
        //defined auditService class with @Transaction with propagation is NotSupport
        //-> suspend @Transactional of caller and keep doing it work
        Assert.assertNull(
                "Task audit data for the update case should not be saved because we threw exception before it.",
                new JPAQuery<Task>(em)
                        .from(QTaskAudit.taskAudit)
                        .where(QTaskAudit.taskAudit.taskName.eq("Task test CreateTaskFromProject")
                                .and(QTaskAudit.taskAudit.auditType.eq(TaskAudit.AuditType.UPDATE)))
                        .fetchFirst());
    }
}
