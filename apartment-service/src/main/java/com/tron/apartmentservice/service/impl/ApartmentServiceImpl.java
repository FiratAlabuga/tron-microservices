package com.tron.apartmentservice.service.impl;

import com.tron.apartmentservice.api.request.ApartmentPaymentRequest;
import com.tron.apartmentservice.domain.Apartment;
import com.tron.apartmentservice.domain.Resident;
import com.tron.apartmentservice.dto.ApartmentDTO;
import com.tron.apartmentservice.event.publisher.ApartmentEventPublisher;
import com.tron.apartmentservice.mapper.ApartmentMapper;
import com.tron.apartmentservice.repository.ApartmentRepository;
import com.tron.apartmentservice.repository.ResidentRepository;
import com.tron.apartmentservice.service.ApartmentService;
import com.tron.event.dto.PaymentCreatedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {
//    private final PaymentKafkaProducer kafkaProducer;
    private final ApartmentEventPublisher apartmentEventPublisher;
    private final ApartmentRepository apartmentRepository;
    private final ResidentRepository residentRepository;
    private final ApartmentMapper apartmentMapper = Mappers.getMapper(ApartmentMapper.class); // Assuming you have a mapper to convert between DTO and entity
    @Override
    public ApartmentDTO getApartmentById(String apartmentId) {
        return null;
    }

    @Override
    public List<ApartmentDTO> getAllApartments() {
        List<Apartment> apartments = apartmentRepository.findAll();
        return apartments.stream()
                .map(apartmentMapper::toDto)
                .toList();
    }

    @Override
    public ApartmentDTO createApartment(ApartmentDTO apartmentDTO) {
        Apartment apartment = apartmentMapper.toEntity(apartmentDTO);
        if (apartment.getApartmentId() == null) {
            throw new RuntimeException("Apartment ID cannot be null");
        }
        Optional<Apartment> existingApartment = apartmentRepository.findByApartmentId(apartment.getApartmentId());
        if (existingApartment.isPresent()) {
            throw new RuntimeException("Apartment with this ID already exists");
        }
        // Save the apartment
        apartmentRepository.save(apartment);
        // Convert to DTO and return
        ApartmentDTO savedApartmentDTO = apartmentMapper.toDto(apartment);
        return savedApartmentDTO;
    }

    @Override
    public ApartmentDTO updateApartment(String apartmentId, ApartmentDTO apartmentDTO) {
        Apartment apartment = apartmentRepository.findByApartmentId(apartmentId)
                .orElseThrow(() -> new RuntimeException("Apartment not found"));
        // Update the apartment details
        apartmentMapper.updateEntity(apartmentDTO, apartment);
        // Convert to DTO and return
        ApartmentDTO updatedApartmentDTO = apartmentMapper.toDto(apartment);
        return updatedApartmentDTO;
    }

    @Override
    public boolean deleteApartment(String apartmentId) {
        Apartment apartment = apartmentRepository.findByApartmentId(apartmentId)
                .orElseThrow(() -> new RuntimeException("Apartment not found"));
        apartment.setStatus(false);
        // Delete the apartment
        apartmentRepository.save(apartment);
        if (apartment.getStatus() == false) {
            return true;
        }
        return false;
    }

    @Override
    public void payBill(ApartmentPaymentRequest apartmentPaymentRequest) {
        Apartment apartment = apartmentRepository.findByApartmentId(apartmentPaymentRequest.getApartmentId())
                .orElseThrow(() -> new RuntimeException("Apartment not found"));
        Resident resident = residentRepository.findByResidentId(apartmentPaymentRequest.getResidentId())
                .orElseThrow(() -> new RuntimeException("Resident not found"));

        if (resident.getBalance().compareTo(apartmentPaymentRequest.getAmount()) < 0) {
            throw new RuntimeException("Yetersiz bakiye");
        }

        apartmentRepository.save(apartment);

        // Kafka'ya mesaj gönder
//        PaymentEventRequest paymentRequest = new PaymentEventRequest();
//        paymentRequest.setResidentId(resident.getResidentId());  // Örnek olarak apartment ID
//        paymentRequest.setApartmentId(apartment.getApartmentId());
//        paymentRequest.setAmount(apartmentPaymentRequest.getAmount());
//        paymentRequest.setPaymentMethod(apartmentPaymentRequest.getPaymentMethodType());
//        paymentRequest.
//        paymentRequest.setPaymentDate(apartmentPaymentRequest.getPaymentDate());
        PaymentCreatedEvent paymentCreatedEvent = new PaymentCreatedEvent();
        paymentCreatedEvent.setSagaId(UUID.randomUUID().toString());
        paymentCreatedEvent.setApartmentId(apartment.getApartmentId());
        paymentCreatedEvent.setResidentId(resident.getResidentId());
        paymentCreatedEvent.setAmount(apartmentPaymentRequest.getAmount());
        paymentCreatedEvent.setPaymentTypeMethod(apartmentPaymentRequest.getPaymentMethodType());
        paymentCreatedEvent.setPaidAt(LocalDateTime.now());
        apartmentEventPublisher.publishPaymentCreatedEvent(paymentCreatedEvent);
        //kafkaProducer.sendPaymentEvent(paymentCreatedEvent);
    }
}
