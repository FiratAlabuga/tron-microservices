package com.tron.apartmentservice.service;

import com.tron.apartmentservice.api.request.ApartmentPaymentRequest;
import com.tron.apartmentservice.dto.ApartmentDTO;

import java.util.List;

public interface ApartmentService {
    ApartmentDTO getApartmentById(String apartmentId);
    List<ApartmentDTO> getAllApartments();
    ApartmentDTO createApartment(ApartmentDTO apartmentDTO);
    ApartmentDTO updateApartment(String apartmentId, ApartmentDTO apartmentDTO);
    boolean deleteApartment(String apartmentId);
    void payBill(ApartmentPaymentRequest apartmentPaymentRequest);
}
