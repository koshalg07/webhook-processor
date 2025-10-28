package com.koshal.webhook.model;

import com.koshal.webhook.model.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventId;
    private String transactionId;
    private BigDecimal amount;
    private String currency;

    private String senderId;
    private String senderName;
    private String receiverId;
    private String receiverName;
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private BigDecimal processingFee;
    private BigDecimal netAmount;
    private BigDecimal exchangeRate;
    private Instant processedAt;

    @Column(updatable = false, insertable = false)
    private Instant createdAt;
    private Instant updatedAt;
}
