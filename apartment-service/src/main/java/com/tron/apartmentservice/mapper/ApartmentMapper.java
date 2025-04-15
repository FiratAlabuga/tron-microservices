package com.tron.apartmentservice.mapper;

import com.tron.apartmentservice.domain.Apartment;
import com.tron.apartmentservice.dto.ApartmentDTO;
import com.tron.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApartmentMapper extends BaseMapper<Apartment, ApartmentDTO> {
    @Override
    Apartment toEntity(ApartmentDTO dto);

    @Override
    ApartmentDTO toDto(Apartment entity);

    @Override
    void updateEntity(ApartmentDTO entity, Apartment dto);
}
