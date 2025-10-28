package com.koshal.webhook.repository;

import com.koshal.webhook.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTransactionId(String transactionId);
    Optional<Transaction> findByEventId(String eventId);
}
