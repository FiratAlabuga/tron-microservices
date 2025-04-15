package com.tron.apartmentservice.api.controller;

import com.tron.apartmentservice.api.request.ApartmentPaymentRequest;
import com.tron.apartmentservice.dto.ApartmentDTO;
import com.tron.apartmentservice.dto.ResidentDTO;
import com.tron.apartmentservice.service.ApartmentService;
import com.tron.api.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "apartmentApiController")
@RequestMapping("v1/api/apartment")
@RequiredArgsConstructor
public class ApartmentApiController {
    private final ApartmentService apartmentService;

    @GetMapping
    public BaseResponse<List<ApartmentDTO>> getAllApartments() {
        List<ApartmentDTO> apartments = apartmentService.getAllApartments();
        return BaseResponse.success(apartments);
    }

    @GetMapping("/{id}")
    public BaseResponse<ApartmentDTO> getApartmentById(@PathVariable String id) {
        ApartmentDTO apartment = apartmentService.getApartmentById(id);
        if (apartment != null) {
            return BaseResponse.success(apartment);
        } else {
            return BaseResponse.error("Apartment not found with id: " + id);
        }
    }

    @PostMapping
    public BaseResponse<ApartmentDTO> createApartment(@RequestBody ApartmentDTO apartmentDTO) {
        ApartmentDTO created = apartmentService.createApartment(apartmentDTO);
        return BaseResponse.success(created);
    }

    @PutMapping("/{id}")
    public BaseResponse<ApartmentDTO> updateApartment(@PathVariable String id, @RequestBody ApartmentDTO apartmentDTO) {
        ApartmentDTO updated = apartmentService.updateApartment(id, apartmentDTO);
        if (updated != null) {
            return BaseResponse.success(updated);
        } else {
            return BaseResponse.error("Failed to update apartment with id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteApartment(@PathVariable String id) {
        boolean deleted = apartmentService.deleteApartment(id);
        if (deleted) {
            return BaseResponse.success(null);
        } else {
            return BaseResponse.error("Failed to delete apartment with id: " + id);
        }
    }
    @PostMapping("/pay")
    public BaseResponse<Void> payBill(@RequestBody ApartmentPaymentRequest apartmentPaymentRequest) {
        try {
            apartmentService.payBill(apartmentPaymentRequest);
            return BaseResponse.success(null);
        } catch (Exception e) {
            return BaseResponse.error("Failed to process payment: " + e.getMessage());
        }
    }
}
