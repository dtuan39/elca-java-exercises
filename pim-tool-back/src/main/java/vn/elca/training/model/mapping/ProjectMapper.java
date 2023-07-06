package vn.elca.training.model.mapping;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.elca.training.model.dto.ProjectDto;
import vn.elca.training.model.entity.Project;

import java.util.Collection;
import java.util.stream.Collectors;
@Mapper(componentModel = "spring")
public abstract class ProjectMapper implements IMapper<Project, ProjectDto> {


    @Override
    @InheritInverseConfiguration
    public abstract Project toEntity(ProjectDto projectDto);

    @Override
    public Collection<Project> toEntities(Collection<ProjectDto> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    @Mapping(target = "projectNumber", source = "projectNumber")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "version", source = "version")
    public abstract ProjectDto toDTO(Project project);

    @Override
    public Collection<ProjectDto> toDTOs(Collection<Project> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
