package vn.elca.training.model.mapping;

import java.util.Collection;

/**
 * @author thongdanghoang
 */
public interface Mapper<Entity, DTO> {
    Entity toEntity(DTO dto);

    Collection<Entity> toEntities(Collection<DTO> dtos);

    DTO toDTO(Entity entity);

    Collection<DTO> toDTOs(Collection<Entity> entities);
}
