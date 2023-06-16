package vn.elca.training.repository;


import org.hibernate.Hibernate;
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
import vn.elca.training.model.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {ApplicationWebConfig.class})
@RunWith(value = SpringRunner.class)
public class GroupRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testSaveGroup() {
        Group group = new Group();
        User user = new User();
        user.setUsername("GiaBao");
        group.setLeader(user);

        Group savedGroup = groupRepository.save(group);

        Assert.assertEquals(group, savedGroup);
    }

    @Test
    @Transactional
    public void testFindById() {
        //define group property
        Group createdGroup = new Group();
        User user = new User();
        user.setUsername("GiaBao");
        Project project = new Project();
        project.setName("Elca Project");
        Set<Project> projectList = new HashSet<>();
        projectList.add(project);

        createdGroup.setLeader(user);
        createdGroup.setProjectList(projectList);
        //save group into db
        groupRepository.save(createdGroup);

        Integer id = createdGroup.getId();
        Optional<Group> optionalGroup  = groupRepository.findById(id);
        //test
        Assert.assertTrue(optionalGroup .isPresent());

        Group retrievedGroup = optionalGroup.get();
        Hibernate.initialize(retrievedGroup.getProjectList());

        Assert.assertEquals(createdGroup, retrievedGroup);
    }
}
