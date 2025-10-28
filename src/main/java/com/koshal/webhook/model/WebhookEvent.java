package com.koshal.webhook.model;

import com.koshal.webhook.model.enums.EventStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "webhook_events")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class WebhookEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String eventId;

    @Lob
    private String payload;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    private Instant receivedAt;
}
