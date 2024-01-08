package pilotproject.Project_Management_ELCA.util;

import org.springframework.stereotype.Component;
import pilotproject.Project_Management_ELCA.model.dto.EmployeeDto;
import pilotproject.Project_Management_ELCA.model.dto.GroupDto;
import pilotproject.Project_Management_ELCA.model.dto.ProjectDto;
import pilotproject.Project_Management_ELCA.model.entity.Employee;
import pilotproject.Project_Management_ELCA.model.entity.Group;
import pilotproject.Project_Management_ELCA.model.entity.Project;

@Component
public class ApplicationMapper {
    public ProjectDto projectToProjectDto(Project entity) {
        ProjectDto dto = new ProjectDto();
        dto.setId(entity.getId());
        dto.setNumber(entity.getProjectNumber());
        dto.setName(entity.getName());
        dto.setCustomer(entity.getCustomer());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setStatus(ProjectDto.StatusDto.valueOf(entity.getStatus().toString()));
        dto.setVersion(entity.getVersion());
        if (entity.getGroup() != null) {
            dto.setGroupLeaderVisa(entity.getGroup().getGroupLeader().getVisa());
            dto.setGroupId(entity.getGroup().getId());
        }
        return dto;
    }

    public Project projectDtoToProjectEntity(ProjectDto projectDto) {
        Project entity = new Project();
        if (projectDto.getId() != null) {
            entity.setId(projectDto.getId());
        }
        entity.setProjectNumber(projectDto.getNumber());
        entity.setCustomer(projectDto.getCustomer());
        entity.setName(projectDto.getName());
        entity.setStatus(Project.Status.valueOf(projectDto.getStatus().toString()));
        entity.setStartDate(projectDto.getStartDate());
        entity.setEndDate(projectDto.getEndDate());
        entity.setVersion(projectDto.getVersion());
        return entity;
    }

    public GroupDto groupToGroupDto(Group entity) {
        GroupDto dto = new GroupDto();
        dto.setId(entity.getId());
        dto.setGroupLeader(employeeToEmployeeDto(entity.getGroupLeader()));
        return dto;
    }

    public EmployeeDto employeeToEmployeeDto(Employee entity) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(entity.getId());
        dto.setVisa(entity.getVisa());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setBirthDate(entity.getBirthDate());
        dto.setVersion(entity.getVersion());
        return dto;
    }

    private Group groupDtoToGroupEntity(GroupDto group) {
        Group groupEntity = new Group();
        groupEntity.setId(group.getId());
        return groupEntity;
    }
}
