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
import java.util.stream.Collectors;

public class ProjectMapperTest {
    // Create an instance of ProjectMapper
    private final ProjectMapper projectMapper = new ProjectMapperImpl();

    @Test
    public void testToDTO() {
        // Suppose we have a Project from the database
        Project project = new Project();
        project.setId(1L);
        project.setProjectNumber(6969);
        project.setName("Sample Project");
        project.setCustomer("Sample Customer");
        project.setStatus(Project.Status.NEW);
        project.setStartDate(LocalDate.now());
        project.setEndDate(null);

        Group group = new Group();
        group.setId(1L);
        HashSet<Employee> employees = new HashSet<>();
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setVisa("FPT-001");
        employees.add(employee);

        project.setGroup(group);
        project.setEmployees(employees);

        ProjectDto projectDto = projectMapper.toDTO(project); // mapping

        // Assert that the mapped ProjectDto has the correct values
        Assertions.assertEquals(project.getGroup().getId(), projectDto.getGroupId());
        Assertions.assertEquals(project.getEmployees()
                        .stream()
                        .map(Employee::getVisa)
                        .collect(Collectors.joining(","))
                , projectDto.getMembers());
        // Assert other fields as needed
        Assertions.assertEquals(project.getId(), projectDto.getId());
        Assertions.assertEquals(project.getProjectNumber(), projectDto.getProjectNumber());
        Assertions.assertEquals(project.getName(), projectDto.getName());
        Assertions.assertEquals(project.getCustomer(), projectDto.getCustomer());
        Assertions.assertEquals(project.getStatus().name(), projectDto.getStatus().name());
        Assertions.assertEquals(project.getStartDate(), projectDto.getStartDate());
        Assertions.assertEquals(project.getEndDate(), projectDto.getEndDate());
        Assertions.assertEquals(project.getVersion(), projectDto.getVersion());
    }

    @Test
    public void testToEntity() {
        // Suppose we have a ProjectDto from the frontend
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(1L);
        projectDto.setName("Facturation / Encaissements");
        projectDto.setCustomer("Les Retaites Populaires");
        projectDto.setProjectNumber(3116);
        projectDto.setStatus(ProjectDto.StatusDto.NEW);
        projectDto.setStartDate(LocalDate.of(2004, 2, 25));
        projectDto.setEndDate(null);
        projectDto.setVersion(1);

        Project project = projectMapper.toEntity(projectDto); // mapping

        // assert all except members and group
        // because mapper cannot map members and group (load from database)
        Assertions.assertEquals(projectDto.getId(), project.getId());
        Assertions.assertEquals(projectDto.getName(), project.getName());
        Assertions.assertEquals(projectDto.getCustomer(), project.getCustomer());
        Assertions.assertEquals(projectDto.getProjectNumber(), project.getProjectNumber());
        Assertions.assertEquals(projectDto.getStatus().name(), project.getStatus().name());
        Assertions.assertEquals(projectDto.getStartDate(), project.getStartDate());
        Assertions.assertEquals(projectDto.getEndDate(), project.getEndDate());
        Assertions.assertEquals(projectDto.getVersion(), project.getVersion());
    }
}
