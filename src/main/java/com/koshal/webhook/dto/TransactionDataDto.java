package com.koshal.webhook.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransactionDataDto {

    @NotBlank
    private String transactionId;

    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a 3-letter ISO code")
    private String currency;

    @Valid
    @NotNull
    private PartyDto sender;

    @Valid
    @NotNull
    private PartyDto receiver;

    @NotBlank
    private String status;

    private String paymentMethod;
}
