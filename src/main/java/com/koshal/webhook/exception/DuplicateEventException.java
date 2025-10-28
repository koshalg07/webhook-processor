package com.koshal.webhook.exception;

import org.springframework.http.HttpStatus;

public class DuplicateEventException extends ApiException {
    public DuplicateEventException(String eventId) {
        super("Duplicate event: " + eventId, HttpStatus.CONFLICT);
    }
}
