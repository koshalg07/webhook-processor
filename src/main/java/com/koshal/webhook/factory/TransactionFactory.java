package com.koshal.webhook.factory;

import com.koshal.webhook.model.Transaction;
import com.koshal.webhook.model.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Factory responsible for creating Transaction entities
 * from validated webhook payloads or DTOs.
 */
public class TransactionFactory {

    private static final BigDecimal PROCESSING_FEE_RATE = new BigDecimal("0.02");

    private TransactionFactory() {}

    public static Transaction create(
            String eventId,
            String transactionId,
            BigDecimal amount,
            String currency,
            String senderId,
            String senderName,
            String senderCountry,
            String receiverId,
            String receiverName,
            String receiverCountry,
            TransactionStatus status,
            String paymentMethod
    ) {
        BigDecimal fee = amount.multiply(PROCESSING_FEE_RATE).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal net = amount.subtract(fee);

        return Transaction.builder()
                .eventId(eventId)
                .transactionId(transactionId)
                .amount(amount)
                .currency(currency)
                .senderId(senderId)
                .senderName(senderName)
                .senderCountry(senderCountry)
                .receiverId(receiverId)
                .receiverName(receiverName)
                .receiverCountry(receiverCountry)
                .status(status)
                .paymentMethod(paymentMethod)
                .processingFee(fee)
                .netAmount(net)
                .processedAt(LocalDateTime.now())
                .build();
    }
}
