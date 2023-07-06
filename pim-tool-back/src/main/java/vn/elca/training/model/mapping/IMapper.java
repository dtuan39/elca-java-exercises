package vn.elca.training.model.mapping;

import java.util.Collection;

/**
 * @author thomas.dang
 * @param <E> Entity
 * @param <D> DTO
 */
public interface IMapper<E, D> {
    E toEntity(D d);

    Collection<E> toEntities(Collection<D> ds);

    D toDTO(E e);

    Collection<D> toDTOs(Collection<E> entities);
}
