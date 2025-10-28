package com.koshal.webhook.service;

import com.koshal.webhook.exception.DuplicateEventException;
import com.koshal.webhook.model.WebhookEvent;
import com.koshal.webhook.model.EventStatus;
import com.koshal.webhook.repository.WebhookEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private final WebhookEventRepository eventRepository;

    @Transactional
    public WebhookEvent ensureUniqueEvent(String eventId, String payload) {
        if (eventRepository.findByEventId(eventId).isPresent()) {
            throw new DuplicateEventException(eventId);
        }

        WebhookEvent event = WebhookEvent.builder()
                .eventId(eventId)
                .payload(payload)
                .status(EventStatus.RECEIVED)
                .build();

        return eventRepository.save(event);
    }
}
