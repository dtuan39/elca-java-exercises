package vn.elca.training.model.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import vn.elca.training.model.dto.GroupDto;
import vn.elca.training.model.entity.Group;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")
public abstract class GroupMapper implements IMapper<Group, GroupDto> {

    @Override
    @Mapping(target = "groupLeader", ignore = true)
    public abstract Group toEntity(GroupDto groupDto);

    @Override
    public List<Group> toEntities(List<GroupDto> groupDtos) {
        return groupDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    @Mapping(source = "groupLeader.visa", target = "groupLeaderVisa")
    public abstract GroupDto toDTO(Group group);

    @Override
    public List<GroupDto> toDTOs(List<Group> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
