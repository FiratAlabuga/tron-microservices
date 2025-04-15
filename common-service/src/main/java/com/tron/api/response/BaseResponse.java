package com.tron.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Tüm API cevaplarının temel yapısı.
 * Başarı durumu, mesaj ve data içerir.
 *
 * @param <T> Response'un taşıyacağı data tipi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private boolean success;
    private String message;
    private T data;

    /**
     * Başarılı response oluşturur
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, "Operation successful", data);
    }

    /**
     * Başarısız response oluşturur
     */
    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(false, message, null);
    }
}