package com.koshal.webhook.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, Object>> handleApi(ApiException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(Map.of(
                        "timestamp", Instant.now(),
                        "error", ex.getMessage(),
                        "status", ex.getStatus().value()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnknown(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(Map.of(
                        "timestamp", Instant.now(),
                        "error", ex.getMessage()
                ));
    }
}
