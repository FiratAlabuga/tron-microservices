package com.tron.domain.enums;

public enum PaymentStatus {
    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED"),
    ROLLBACK("ROLLBACK");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
