package com.tron.apartmentservice.repository;

import com.tron.apartmentservice.domain.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidentRepository extends JpaRepository<Resident,Long> {
    Optional<Resident> findByResidentId(String residentId);
    Resident findByApartmentIdAndBlockAndNumberAndResidentId(String apartmentId, String block, String number, String residentId);
    Resident findByApartmentIdAndBlockAndNumber(String apartmentId, String block, String number);
    Resident findByApartmentIdAndBlock(String apartmentId, String block);
    Resident findByApartmentIdAndNumber(String apartmentId, String number);
    Resident findByApartmentId(String apartmentId);
}
