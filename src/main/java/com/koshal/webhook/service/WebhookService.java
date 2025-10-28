package com.koshal.webhook.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koshal.webhook.dto.WebhookPayloadDto;
import com.koshal.webhook.factory.TransactionFactory;
import com.koshal.webhook.model.*;
import com.koshal.webhook.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class WebhookService {

    private final IdempotencyService idempotencyService;
    private final TransactionRepository transactionRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    @Transactional
    public void processWebhook(WebhookPayloadDto payload) {
        try {
            String rawPayload = mapper.writeValueAsString(payload);
            idempotencyService.ensureUniqueEvent(payload.getEventId(), rawPayload);

            var data = payload.getData();
            Transaction transaction = TransactionFactory.create(
                    payload.getEventId(),
                    data.getTransactionId(),
                    data.getAmount(),
                    data.getCurrency(),
                    data.getSender().getId(),
                    data.getSender().getName(),
                    data.getSender().getCountry(),
                    data.getReceiver().getId(),
                    data.getReceiver().getName(),
                    data.getReceiver().getCountry(),
                    TransactionStatus.valueOf(data.getStatus().toUpperCase()),
                    data.getPaymentMethod()
            );

            transactionRepository.save(transaction);

        } catch (Exception e) {
            throw new RuntimeException("Webhook processing failed: " + e.getMessage(), e);
        }
    }
}
