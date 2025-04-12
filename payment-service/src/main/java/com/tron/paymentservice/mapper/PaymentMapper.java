package com.tron.paymentservice.mapper;

import com.tron.mapper.BaseMapper;
import com.tron.paymentservice.domain.Payment;
import com.tron.paymentservice.dto.PaymentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper extends BaseMapper<Payment, PaymentDTO> {
    // Define any additional mapping methods specific to Payment if needed
    // For example, if you have a method to map from PaymentDTO to Payment
    Payment toEntity(PaymentDTO paymentDTO);
    // If you need to map from Payment to PaymentDTO
    PaymentDTO toDto(Payment payment);
    // If you need to update an existing Payment entity with values from PaymentDTO
    void updateEntity(Payment payment, PaymentDTO paymentDTO);


}
