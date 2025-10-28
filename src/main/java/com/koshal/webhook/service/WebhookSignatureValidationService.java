package com.koshal.webhook.service;

import com.koshal.webhook.exception.GlobalExceptionHandler.ApiException;
import com.koshal.webhook.util.HmacSignatureValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service for validating webhook signatures and timestamps
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WebhookSignatureValidationService {

    private final HmacSignatureValidator signatureValidator;

    @Value("${webhook.secret}")
    private String webhookSecret;

    @Value("${webhook.tolerance-seconds:300}")
    private int toleranceSeconds;

    /**
     * Validates webhook signature and timestamp
     * 
     * @param payload The raw request body
     * @param signature The signature from X-Webhook-Signature header
     * @param timestamp The timestamp from the webhook payload (in seconds)
     * @throws ApiException if validation fails
     */
    public void validateWebhook(String payload, String signature, long timestamp) {
        log.debug("Validating webhook signature and timestamp");
        
        // Validate signature
        if (!signatureValidator.isValidSignature(payload, signature, webhookSecret)) {
            log.error("Webhook signature validation failed");
            throw new ApiException("Invalid webhook signature", HttpStatus.UNAUTHORIZED);
        }
        
        // Validate timestamp
        if (!signatureValidator.isValidTimestamp(timestamp, toleranceSeconds)) {
            log.error("Webhook timestamp validation failed");
            throw new ApiException("Webhook timestamp is outside tolerance window", HttpStatus.UNAUTHORIZED);
        }
        
        log.debug("Webhook validation successful");
    }

}
