package com.koshal.webhook.controller;

import com.koshal.webhook.dto.WebhookPayloadDto;
import com.koshal.webhook.service.WebhookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;

    @PostMapping("/payment")
    public ResponseEntity<Map<String, Object>> handleWebhook(@Valid @RequestBody WebhookPayloadDto payload) {
        webhookService.processWebhook(payload);
        return ResponseEntity.ok(Map.of(
                "message", "Webhook received successfully",
                "eventId", payload.getEventId()
        ));
    }
}
