package com.tron.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Tüm mapper interface'lerinin türeteceği temel interface.
 * DTO ve Entity arasında dönüşüm sağlar.
 *
 * @param <D> DTO tipi
 * @param <E> Entity tipi
 */
public interface BaseMapper<D, E> {
    /**
     * DTO'dan Entity'ye dönüşüm yapar
     */
    E toEntity(D dto);

    /**
     * Entity'den DTO'ya dönüşüm yapar
     */
    D toDto(E entity);

    /**
     * Entity'yi DTO'dan gelen değerlerle günceller
     * @param entity Güncellenecek entity
     * @param dto Kaynak DTO
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget E entity, D dto);
}
