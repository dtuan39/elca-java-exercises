package vn.elca.training.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.ApplicationWebConfig;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.entity.QGroup;
import vn.elca.training.model.entity.QProject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ContextConfiguration(classes = {ApplicationWebConfig.class})
@RunWith(value = SpringRunner.class)
public class ProjectRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testCountAll() {
        projectRepository.save(new Project("KSTA", LocalDate.now()));
        projectRepository.save(new Project("LAGAPEO", LocalDate.now()));
        projectRepository.save(new Project("ZHQUEST", LocalDate.now()));
        projectRepository.save(new Project("SECUTIX", LocalDate.now()));
        Assert.assertEquals(9, projectRepository.count());
    }

    @Test
    public void testFindOneWithQueryDSL() {
        final String PROJECT_NAME = "KSTA";
        projectRepository.save(new Project(PROJECT_NAME, LocalDate.now()));
        Project project = new JPAQuery<Project>(em).from(QProject.project).where(QProject.project.name.eq(PROJECT_NAME)).fetchFirst();
        Assert.assertEquals(PROJECT_NAME, project.getName());
    }

    @Test
    @Transactional
    public void testSaveProject() { //To verify the saving of one project via ProjectRepository
        // Create a project
        Project project = new Project();
        project.setName("Sample Project");
        project.setFinishingDate(LocalDate.of(2023, 6, 30));
        project.setCustomer("ABC Company");

        // Save the project via the repository
        Project savedProject = projectRepository.save(project);

        // Flush and clear the EntityManager to synchronize the persistence context
        em.flush(); //A call to EntityManager.flush(); will force the data to be persisted in the database immediately
        em.clear(); //Clear the persistence context, causing all managed entities to become detached.
        //Using flush() and clear() together helps ensure that the test operates on a clean state and avoids any interference from previously managed entities or cached data.

        // Retrieve the project from the EntityManager by ID
        Project retrievedProject = em.find(Project.class, savedProject.getId());

        // Assertions
        Assert.assertNotNull(retrievedProject);
        Assert.assertEquals("Sample Project", retrievedProject.getName());
        Assert.assertEquals(LocalDate.of(2023, 6, 30), retrievedProject.getFinishingDate());
        Assert.assertEquals("ABC Company", retrievedProject.getCustomer());
    }


    @Test
    @Transactional
    public void testSaveMultipleProjects() {
        // Create projects
        Project project1 = new Project();
        project1.setName("Project 1");
        project1.setFinishingDate(LocalDate.of(2023, 6, 30));
        project1.setCustomer("Customer 1");

        Project project2 = new Project();
        project2.setName("Project 2");
        project2.setFinishingDate(LocalDate.of(2023, 6, 30));
        project2.setCustomer("Customer 2");

        Project project3 = new Project();
        project3.setName("Project 3");
        project3.setFinishingDate(LocalDate.of(2023, 6, 30));
        project3.setCustomer("Customer 3");

        // Save the projects via the repository
        List<Project> projectList = List.of(project1, project2, project3);
        projectRepository.saveAll(projectList);

        // Flush and clear the EntityManager to synchronize the persistence context
        em.flush();
        em.clear();

        // Retrieve the projects from the EntityManager by ID
        Project retrievedProject1 = em.find(Project.class, project1.getId());
        Project retrievedProject2 = em.find(Project.class, project2.getId());
        Project retrievedProject3 = em.find(Project.class, project3.getId());

        // Assertions
        Assert.assertNotNull(retrievedProject1);
        Assert.assertNotNull(retrievedProject2);
        Assert.assertNotNull(retrievedProject3);
        Assert.assertEquals("Project 1", retrievedProject1.getName());
        Assert.assertEquals("Project 2", retrievedProject2.getName());
        Assert.assertEquals("Project 3", retrievedProject3.getName());
    }

    @Test
    @Transactional
    public void testDeleteProject() {
        // Create a project
        Project project = new Project();
        project.setName("Test Project");
        project.setFinishingDate(LocalDate.of(2023, 6, 30));
        project.setCustomer("Test Customer");

        // Save the project
        projectRepository.save(project);

        // Delete the project
        projectRepository.delete(project);

        // Flush and clear the EntityManager to synchronize the persistence context
        em.flush();
        em.clear();

        // Try to retrieve the project by ID
        Optional<Project> optionalProject = projectRepository.findById(project.getId());

        // Assertion
        Assert.assertFalse(optionalProject.isPresent());
    }

    @Test
    @Transactional
    public void findProjectsByNameAndCustomer() {
        // Create projects
        Project project1 = new Project(); //Project 1
        project1.setName("Project 1");
        project1.setFinishingDate(LocalDate.of(2023, 6, 30));
        project1.setCustomer("Customer 1");
        Project project2 = new Project(); //Project 2
        project2.setName("Project 2");
        project2.setFinishingDate(LocalDate.of(2023, 6, 30));
        project2.setCustomer("Customer 2");

        projectRepository.save(project1);
        projectRepository.save(project2);

        em.flush();
        em.clear();

        String name = "%Pro%";
        String customer = "%Cus%";
        List<Project> projects = new JPAQuery<Project>(em).from(QProject.project).where(QProject.project.name.like(name).and(QProject.project.customer.like(customer))).fetch();

        int expectedSize = 2;
        Assert.assertEquals(projects.size(), expectedSize);
    }

    @Test
    @Transactional
    public void findProjectNameByGroupId() {
        Group group1 = new Group();

        Project project1 = new Project();
        project1.setName("Project 1");  //Project 1
        project1.setFinishingDate(LocalDate.of(2023, 6, 30));
        project1.setCustomer("Customer 1");
        Project project2 = new Project(); //Project 2
        project2.setName("Project 2");
        project2.setFinishingDate(LocalDate.of(2023, 6, 30));
        project2.setCustomer("Customer 2");

        Set<Project> projectList = new HashSet<>();
        projectList.add(project1);
        projectList.add(project2);
        group1.setProjectList(projectList);

        projectRepository.saveAll(projectList);
        groupRepository.save(group1);

        em.flush();
        em.clear();

        int groupId = group1.getId();

        QProject qProject = QProject.project;
        QGroup qGroup = QGroup.group;

        List<Project> groupProjects = new JPAQuery<Project>(em)
                .select(qProject)
                .from(qGroup)
                .join(qGroup.projectList, qProject)
                .where(qGroup.id.eq(groupId))
                .fetch();
        //SELECT *
        //FROM GROUP_TABLE g JOIN GROUP_PROJECT_TABLE gj
        //ON g.id = gj.GROUP_ID
        //JOIN PROJECT p
        //ON gj.PROJECT_ID = p.ID
        //WHERE g.ID = ?

        Assert.assertEquals(2,groupProjects.size());
    }
}
