package vn.elca.training.model.mapping;

import java.util.Collection;
import java.util.List;

/**
 * @author thomas.dang
 * @param <E> Entity
 * @param <D> DTO
 */
public interface IMapper<E, D> {
    E toEntity(D d);

    List<E> toEntities(List<D> ds);

    D toDTO(E e);

    List<D> toDTOs(List<E> entities);
}
