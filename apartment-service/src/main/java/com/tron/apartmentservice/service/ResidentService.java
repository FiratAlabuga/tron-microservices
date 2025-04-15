package com.tron.apartmentservice.service;

import com.tron.apartmentservice.dto.ResidentDTO;

import java.util.List;

public interface ResidentService {
    ResidentDTO getResidentById(String residentId);

    List<ResidentDTO> getAllResidents();

    ResidentDTO createResident(ResidentDTO residentDTO);

    ResidentDTO updateResident(String residentId, ResidentDTO residentDTO);

    boolean deleteResident(String residentId);
}
