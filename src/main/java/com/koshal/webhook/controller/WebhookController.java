package com.koshal.webhook.controller;

import com.koshal.webhook.dto.WebhookPayloadDto;
import com.koshal.webhook.exception.GlobalExceptionHandler.ApiException;
import com.koshal.webhook.service.WebhookService;
import com.koshal.webhook.service.WebhookSignatureValidationService;
import com.koshal.webhook.util.CachedBodyHttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;
    private final WebhookSignatureValidationService signatureValidationService;

    @PostMapping("/payment")
    public ResponseEntity<?> receiveWebhook(
            @Valid @RequestBody WebhookPayloadDto payload,
            @RequestHeader("X-Webhook-Signature") String signature,
            CachedBodyHttpServletRequest request) {
        
        try {
            String rawBody = request.getCachedBody();
            
            // Validate webhook signature and timestamp
            signatureValidationService.validateWebhook(
                rawBody, 
                signature, 
                payload.getTimestamp().getEpochSecond()
            );

            webhookService.processWebhook(payload);
            
            return ResponseEntity.ok(
                    java.util.Map.of(
                            "eventId", payload.getEventId(),
                            "message", "Webhook received and processed successfully"
                    )
            );
            
        } catch (ApiException ex) {
            log.error("API Exception: {}", ex.getMessage());
            return ResponseEntity.status(ex.getStatus())
                    .body(java.util.Map.of(
                            "error", ex.getMessage(),
                            "status", ex.getStatus().value()
                    ));
        } catch (Exception e) {
            log.error("Error processing webhook", e);
            return ResponseEntity.badRequest()
                    .body(java.util.Map.of(
                            "error", "Failed to process webhook",
                            "message", e.getMessage()
                    ));
        }
    }
}
