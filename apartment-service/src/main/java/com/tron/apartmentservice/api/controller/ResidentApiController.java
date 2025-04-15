package com.tron.apartmentservice.api.controller;

import com.tron.apartmentservice.dto.ResidentDTO;
import com.tron.apartmentservice.service.ResidentService;
import com.tron.api.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/residents")
@RequiredArgsConstructor
public class ResidentApiController {

    private final ResidentService residentService;

    @GetMapping
    public BaseResponse<List<ResidentDTO>> getAllResidents() {
        List<ResidentDTO> residents = residentService.getAllResidents();
        return BaseResponse.success(residents);
    }

    @GetMapping("/{id}")
    public BaseResponse<ResidentDTO> getResidentById(@PathVariable String id) {
        ResidentDTO resident = residentService.getResidentById(id);
        if (resident != null) {
            return BaseResponse.success(resident);
        } else {
            return BaseResponse.error("Resident not found with id: " + id);
        }
    }

    @PostMapping
    public BaseResponse<ResidentDTO> createResident(@RequestBody ResidentDTO residentDTO) {
        ResidentDTO created = residentService.createResident(residentDTO);
        return BaseResponse.success(created);
    }

    @PutMapping("/{id}")
    public BaseResponse<ResidentDTO> updateResident(@PathVariable String id, @RequestBody ResidentDTO residentDTO) {
        ResidentDTO updated = residentService.updateResident(id, residentDTO);
        if (updated != null) {
            return BaseResponse.success(updated);
        } else {
            return BaseResponse.error("Failed to update resident with id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteResident(@PathVariable String id) {
        boolean deleted = residentService.deleteResident(id);
        if (deleted) {
            return BaseResponse.success(null);
        } else {
            return BaseResponse.error("Failed to delete resident with id: " + id);
        }
    }

}