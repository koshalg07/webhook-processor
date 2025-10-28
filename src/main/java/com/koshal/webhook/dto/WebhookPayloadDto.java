package com.koshal.webhook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.Instant;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class WebhookPayloadDto {

    @NotBlank
    @JsonProperty("event_id")
    private String eventId;

    @NotBlank
    @JsonProperty("event_type")
    private String eventType;

    @NotNull
    private Instant timestamp;

    @Valid @NotNull
    private TransactionDataDto data;
}
