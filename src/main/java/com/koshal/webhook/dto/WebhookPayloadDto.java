package com.koshal.webhook.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WebhookPayloadDto {

    @NotBlank
    private String eventId;

    @NotBlank
    private String eventType;

    @NotBlank
    private String timestamp;

    @Valid
    @NotNull
    private TransactionDataDto data;
}
