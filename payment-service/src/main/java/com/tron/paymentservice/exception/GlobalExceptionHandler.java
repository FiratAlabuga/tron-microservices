package com.tron.paymentservice.exception;

import com.tron.exception.BaseException;
import com.tron.exception.provider.LocalizedMessageProvider;
import com.tron.exception.response.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final LocalizedMessageProvider messageProvider;

    /**
     * Handles BaseException and returns a ResponseEntity with a localized error message.
     *
     * @param ex     The BaseException to handle.
     * @param locale The locale for localization.
     * @return A ResponseEntity containing the error response.
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex, Locale locale) {
        String message = messageProvider.getMessage(ex.getMessageKey(), locale, ex.getArgs());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessageKey().name(), message));
    }
}