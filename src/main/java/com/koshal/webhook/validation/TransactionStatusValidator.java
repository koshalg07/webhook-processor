package com.koshal.webhook.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

/**
 * Validator for transaction status values
 * Validates that the status is one of the allowed values: pending, completed, failed
 */
public class TransactionStatusValidator implements ConstraintValidator<ValidTransactionStatus, String> {

    // Set of valid transaction status values
    private static final Set<String> VALID_STATUSES = Set.of(
        "pending",
        "completed", 
        "failed"
    );

    @Override
    public void initialize(ValidTransactionStatus constraintAnnotation) {
    }

    @Override
    public boolean isValid(String status, ConstraintValidatorContext context) {
        if (status == null || status.trim().isEmpty()) {
            return true; // Let @NotBlank handle null/empty validation
        }
        
        return VALID_STATUSES.contains(status.toLowerCase());
    }
}
