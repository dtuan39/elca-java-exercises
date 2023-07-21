package vn.elca.training.mapping;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;
import vn.elca.training.model.mapping.ProjectMapper;
import vn.elca.training.model.mapping.ProjectMapperImpl;

import java.time.LocalDate;
import java.util.HashSet;

public class ProjectMapperTest {
    // Create an instance of ProjectMapper
    private final ProjectMapper projectMapper = new ProjectMapperImpl();

    @Test
    public void testToDTO() {
        // Create a sample Project entity
        Project project = new Project();
        project.setId(1L);
        project.setProjectNumber(6969);
        Group group = new Group();
        group.setId(1L);
        project.setGroup(group);
        HashSet<Employee> employees = new HashSet<>();
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setVisa("FPT-001");
        employees.add(employee);
        project.setEmployees(employees);
        project.setName("Sample Project");
        project.setCustomer("Sample Customer");
        project.setStatus(Project.Status.NEW);
        project.setStartDate(LocalDate.now());
        project.setEndDate(null);
        // Set other properties of the project as needed

        // Call the mapper to convert Project to ProjectDto
        ProjectDto projectDto = projectMapper.toDTO(project);

        // print projectDto to see the result
        System.out.println(projectDto);

        // Assert that the mapped ProjectDto has the correct values
        Assertions.assertEquals(project.getId(), projectDto.getId());
        Assertions.assertEquals(project.getGroup().getId(), projectDto.getGroupId());
        // Assert other fields as needed
    }

    @Test
    public void testToEntity() {
        ProjectDto projectDto = new ProjectDto();
        //{
        //    "id": 1,
        //    "name": "Facturation / Encaissements",
        //    "customer": "Les Retaites Populaires",
        //    "groupId": 1,
        //    "members": "ABC,DEF",
        //    "status": "NEW",
        //    "startDate": "2004-02-25",
        //    "endDate": null,
        //    "version": 1,
        //    "number": 3116
        //}
        projectDto.setId(1L);
        projectDto.setName("Facturation / Encaissements");
        projectDto.setCustomer("Les Retaites Populaires");
        projectDto.setProjectNumber(3116);
        projectDto.setStatus(ProjectDto.StatusDto.NEW);
        projectDto.setStartDate(LocalDate.of(2004, 2, 25));
        projectDto.setEndDate(null);
        projectDto.setVersion(1);
        projectDto.setMembers("ABC,DEF");
        projectDto.setGroupId(1L);

        Project project = projectMapper.toEntity(projectDto);

        System.out.println(project);
    }
}
