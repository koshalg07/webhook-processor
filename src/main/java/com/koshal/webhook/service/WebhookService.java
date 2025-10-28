package com.koshal.webhook.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koshal.webhook.dto.WebhookPayloadDto;
import com.koshal.webhook.exception.GlobalExceptionHandler.ApiException;
import com.koshal.webhook.model.Transaction;
import com.koshal.webhook.model.WebhookEvent;
import com.koshal.webhook.model.enums.EventStatus;
import com.koshal.webhook.model.enums.TransactionStatus;
import com.koshal.webhook.repository.TransactionRepository;
import com.koshal.webhook.repository.WebhookEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final TransactionRepository transactionRepository;
    private final WebhookEventRepository webhookEventRepository;
    private final ObjectMapper mapper;

    @Transactional
    public void processWebhook(WebhookPayloadDto payload) {
        try {
            // Idempotency check
            webhookEventRepository.findByEventId(payload.getEventId()).ifPresent(e -> {
                throw new ApiException("Duplicate event: " + payload.getEventId(), HttpStatus.CONFLICT);
            });

            webhookEventRepository.save(WebhookEvent.builder()
                    .eventId(payload.getEventId())
                    .payload(mapper.writeValueAsString(payload))
                    .status(EventStatus.RECEIVED)
                    .receivedAt(Instant.now())
                    .build());

            var data = payload.getData();

            BigDecimal processingFee = data.getAmount().multiply(BigDecimal.valueOf(0.02));
            BigDecimal netAmount = data.getAmount().subtract(processingFee);

            Transaction transaction = Transaction.builder()
                    .eventId(payload.getEventId())
                    .transactionId(data.getTransactionId())
                    .amount(data.getAmount())
                    .currency(data.getCurrency())
                    .senderId(data.getSender().getId())
                    .senderName(data.getSender().getName())
                    .senderCountry(data.getSender().getCountry())
                    .receiverId(data.getReceiver().getId())
                    .receiverName(data.getReceiver().getName())
                    .receiverCountry(data.getReceiver().getCountry())
                    .paymentMethod(data.getPaymentMethod())
                    .status(TransactionStatus.valueOf(data.getStatus().toUpperCase()))
                    .processingFee(processingFee)
                    .netAmount(netAmount)
                    .exchangeRate(BigDecimal.ONE)
                    .processedAt(Instant.now())
                    .build();

            transactionRepository.save(transaction);

        } catch (ApiException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ApiException("Webhook processing failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
