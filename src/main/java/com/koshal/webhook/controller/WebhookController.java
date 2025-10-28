package com.koshal.webhook.controller;

import com.koshal.webhook.dto.WebhookPayloadDto;
import com.koshal.webhook.service.WebhookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;

    @PostMapping("/payment")
    public ResponseEntity<?> receiveWebhook(@Valid @RequestBody WebhookPayloadDto payload) {
        webhookService.processWebhook(payload);
        return ResponseEntity.ok(
                java.util.Map.of(
                        "eventId", payload.getEventId(),
                        "message", "Webhook received successfully"
                )
        );
    }
}
