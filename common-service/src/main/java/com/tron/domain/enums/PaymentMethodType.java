package com.tron.domain.enums;

public enum PaymentMethodType {
    CASH("CASH"),
    CREDIT_CARD("CREDIT_CARD"),
    DEBIT_CARD("DEBIT_CARD"),
    BANK_TRANSFER("BANK_TRANSFER"),
    MOBILE_PAYMENT("MOBILE_PAYMENT"),
    CRYPTOCURRENCY("CRYPTOCURRENCY");

    private final String value;

    PaymentMethodType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PaymentMethodType fromValue(String value) {
        for (PaymentMethodType method : PaymentMethodType.values()) {
            if (method.value.equalsIgnoreCase(value)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Unknown payment method type: " + value);
    }
}
