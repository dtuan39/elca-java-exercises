package vn.elca.training.model.mapping;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Employee;
import vn.elca.training.model.entity.Project;

import java.util.Collection;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Component
public abstract class ProjectMapper implements IMapper<Project, ProjectDto> {


    @Override
    @InheritInverseConfiguration
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "employees", ignore = true)
    public abstract Project toEntity(ProjectDto projectDto);

    @Override
    public Collection<Project> toEntities(Collection<ProjectDto> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "status", expression = "java(getStatus(project.getStatus()))")
    @Mapping(target = "members", expression = "java(getMembers(project))")
    public abstract ProjectDto toDTO(Project project);

    @Override
    public Collection<ProjectDto> toDTOs(Collection<Project> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    protected ProjectDto.StatusDto getStatus(Project.Status status) {
        return ProjectDto.StatusDto.valueOf(status.toString());
    }

    protected String getMembers(Project project) {
        return project.getEmployees()
                .stream()
                .map(Employee::getVisa)
                .collect(Collectors.joining(","));
    }
}
