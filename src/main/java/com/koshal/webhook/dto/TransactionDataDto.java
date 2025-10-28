package com.koshal.webhook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class TransactionDataDto {

    @NotBlank
    @JsonProperty("transaction_id")
    private String transactionId;

    @NotNull @DecimalMin("0.01")
    private BigDecimal amount;

    @NotBlank
    private String currency;

    @Valid @NotNull
    private PartyDto sender;

    @Valid @NotNull
    private PartyDto receiver;

    @NotBlank
    private String status;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @Valid
    private MetadataDto metadata;
}
