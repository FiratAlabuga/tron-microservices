package com.tron.apartmentservice.repository;

import com.tron.apartmentservice.domain.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment,Long> {
    Optional<Apartment> findByApartmentId(String apartmentId);
    Apartment findByApartmentIdAndBlockAndNumber(String apartmentId, String block, String number);
    Apartment findByApartmentIdAndBlock(String apartmentId, String block);
    Apartment findByApartmentIdAndNumber(String apartmentId, String number);
    Apartment findByApartmentIdAndBlockAndNumberAndResidentId(String apartmentId, String block, String number, String residentId);
    Apartment findByApartmentIdAndResidentId(String apartmentId, String residentId);
}
