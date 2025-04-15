package com.tron.apartmentservice.service.impl;

import com.tron.apartmentservice.domain.Resident;
import com.tron.apartmentservice.dto.ResidentDTO;
import com.tron.apartmentservice.mapper.ResidentMapper;
import com.tron.apartmentservice.repository.ResidentRepository;
import com.tron.apartmentservice.service.ResidentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ResidentServiceImpl implements ResidentService {
    private final ResidentRepository residentRepository;
    private final ResidentMapper residentMapper = Mappers.getMapper(ResidentMapper.class);

    @Override
    public ResidentDTO getResidentById(String residentId) {
        Resident resident = residentRepository.findByResidentId(residentId)
                .orElseThrow(() -> new RuntimeException("Resident not found"));
        // Implementation here
        ResidentDTO residentDTO = residentMapper.toDto(resident);
        // Convert to DTO and return
        return residentDTO;
    }

    @Override
    public List<ResidentDTO> getAllResidents() {
        // Implementation here
        List<Resident> residents = residentRepository.findAll();
        return residents.stream()
                .map(residentMapper::toDto)
                .toList();
    }

    @Override
    public ResidentDTO createResident(ResidentDTO residentDTO) {
        // Implementation here
        Resident resident = residentMapper.toEntity(residentDTO);
        if (resident.getResidentId() == null) {
            throw new RuntimeException("Resident ID cannot be null");
        }
        // Check if the resident already exists
        if (residentRepository.findByResidentId(resident.getResidentId()).isPresent()) {
            throw new RuntimeException("Resident with this ID already exists");
        }
        // Save the resident
        residentRepository.save(resident);
        // Convert to DTO and return
        ResidentDTO savedResidentDTO = residentMapper.toDto(resident);
        // Optionally, you can publish an event here if needed
        // kafkaProducer.sendResidentCreatedEvent(savedResidentDTO);
        return savedResidentDTO;
    }

    @Override
    public ResidentDTO updateResident(String residentId, ResidentDTO residentDTO) {
        // Implementation here
        Resident resident = residentRepository.findByResidentId(residentId)
                .orElseThrow(() -> new RuntimeException("Resident not found"));
        // Update the resident details
        residentMapper.updateEntity(residentDTO, resident);
        // Save the updated resident
        residentRepository.save(resident);
        // Convert to DTO and return
        ResidentDTO updatedResidentDTO = residentMapper.toDto(resident);
        // Optionally, you can publish an event here if needed
        // kafkaProducer.sendResidentUpdatedEvent(updatedResidentDTO);
        return updatedResidentDTO;
    }

    @Override
    public boolean deleteResident(String residentId) {
        // Implementation here
        Resident resident = residentRepository.findByResidentId(residentId)
                .orElseThrow(() -> new RuntimeException("Resident not found"));
        // Delete the resident
        resident.setStatus(false);
        residentRepository.save(resident);
        if (resident.getStatus() == false) {
            return true;
        }
        return false;
    }
}
