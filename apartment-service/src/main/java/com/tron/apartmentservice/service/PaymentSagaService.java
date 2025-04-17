package com.tron.apartmentservice.service;

import com.tron.apartmentservice.api.request.ApartmentPaymentRequest;

public interface PaymentSagaService {
    void startPaymentSaga(ApartmentPaymentRequest apartmentPaymentRequest);
}
