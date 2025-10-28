CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id VARCHAR(255) NOT NULL UNIQUE,
    transaction_id VARCHAR(255) NOT NULL UNIQUE,
    amount DECIMAL(18,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    sender_id VARCHAR(255),
    sender_name VARCHAR(255),
    receiver_id VARCHAR(255),
    receiver_name VARCHAR(255),
    payment_method VARCHAR(100),
    status VARCHAR(20) NOT NULL,
    processing_fee DECIMAL(18,2),
    net_amount DECIMAL(18,2),
    exchange_rate DECIMAL(18,4) DEFAULT 1.0000,
    processed_at DATETIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
