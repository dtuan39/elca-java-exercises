package vn.elca.training.util;

import org.springframework.stereotype.Component;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Group;
import vn.elca.training.model.entity.Project;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gtn
 */
@Component
public class ApplicationMapper {
    public ApplicationMapper() {
        // Mapper utility class
    }

    public ProjectDto projectToProjectDto(Project entity) {
        ProjectDto dto = new ProjectDto();
        dto.setId(entity.getId());
        dto.setProjectNumber(entity.getProjectNumber());
        dto.setName(entity.getName());
        dto.setCustomer(entity.getCustomer());
        dto.setGroupId(entity.getGroup().getId());//lazy loading
        dto.setStatus(entity.getStatus().toString());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        //set string members split by , by visa
        dto.setMembers(entity.getEmployees().stream().map(Employee::getVisa).collect(Collectors.joining(",")));
        dto.setVersion(entity.getVersion());
        return dto;
    }

    public Project projectDtoToProject(ProjectDto projectDto) {
        Project project = new Project();
        project.setProjectNumber(projectDto.getProjectNumber());
        project.setName(projectDto.getName());
        project.setCustomer(projectDto.getCustomer());
        project.setStatus(Project.Status.valueOf(projectDto.getStatus()));
        project.setStartDate(projectDto.getStartDate());
        project.setEndDate(projectDto.getEndDate());
        //Entity needn't version because it is auto generated
        return project;
    }

    public GroupDto groupToGroupDto(Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setGroupLeaderVisa(group.getGroupLeader().getVisa());
        return groupDto;
    }

    public List<ProjectDto> projectToProjectDto(List<Project> entities) {
        return entities.stream().map(this::projectToProjectDto).collect(Collectors.toList());
    }
}
