package com.koshal.webhook.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom validation annotation for ISO 4217 currency codes
 * Validates that the currency field contains a valid 3-letter currency code
 */
@Documented
@Constraint(validatedBy = CurrencyValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCurrency {
    String message() default "Currency must be a valid ISO 4217 currency code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
