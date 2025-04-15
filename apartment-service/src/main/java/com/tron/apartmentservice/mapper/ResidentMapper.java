package com.tron.apartmentservice.mapper;

import com.tron.apartmentservice.domain.Resident;
import com.tron.apartmentservice.dto.ResidentDTO;
import com.tron.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResidentMapper extends BaseMapper<Resident, ResidentDTO> {
    @Override
    Resident toEntity(ResidentDTO dto);

    @Override
    ResidentDTO toDto(Resident entity);

    @Override
    void updateEntity(ResidentDTO entity, Resident dto);
}
