package com.koshal.webhook.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom validation annotation for transaction status
 * Validates that the status field contains a valid transaction status
 */
@Documented
@Constraint(validatedBy = TransactionStatusValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTransactionStatus {
    String message() default "Status must be one of: pending, completed, failed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
