package com.koshal.webhook.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

/**
 * Validator for ISO 4217 currency codes
 */
public class CurrencyValidator implements ConstraintValidator<ValidCurrency, String> {

    // Set of valid ISO 4217 currency codes
    private static final Set<String> VALID_CURRENCIES = Set.of(
        // Major currencies
        "USD", "EUR", "GBP", "JPY", "CHF", "CAD", "AUD", "NZD",
        
        // Asian currencies
        "CNY", "INR", "KRW", "THB", "SGD", "MYR", "PHP", "VND", "IDR", "HKD",
        
        // Middle East & Africa
        "AED", "SAR", "QAR", "KWD", "BHD", "OMR", "JOD", "ILS", "TRY",
        "ZAR", "EGP", "NGN", "KES", "GHS", "MAD", "TND", "DZD",
        
        // European currencies
        "SEK", "NOK", "DKK", "PLN", "CZK", "HUF", "RON", "BGN", "HRK",
        
        // Latin American currencies
        "BRL", "MXN", "ARS", "CLP", "COP", "PEN", "UYU", "BOB", "VES",
        
        // Other major currencies
        "RUB", "UAH", "KZT", "PKR", "BDT", "LKR", "NPR", "MMK", "KHR",
        
        
        "AFN", "ALL", "AMD", "ANG", "AOA", "AWG", "AZN", "BAM", "BBD",
        "BIF", "BMD", "BND", "BSD", "BTN", "BWP", "BYN", "BZD", "CDF",
        "CRC", "CUP", "CVE", "DJF", "DOP", "ERN", "ETB", "FJD", "FKP",
        "GEL", "GIP", "GMD", "GNF", "GTQ", "GYD", "HNL", "HTG", "IQD",
        "IRR", "ISK", "JMD", "KGS", "KMF", "KPW", "KYD", "LAK", "LBP",
        "LRD", "LSL", "LYD", "MDL", "MGA", "MKD", "MNT", "MOP", "MRU",
        "MUR", "MVR", "MWK", "MZN", "NAD", "NIO", "PAB", "PGK", "PYG",
        "RSD", "RWF", "SBD", "SCR", "SDG", "SHP", "SLE", "SLL", "SOS",
        "SRD", "STN", "SYP", "SZL", "TJS", "TMT", "TOP", "TTD", "TVD",
        "TWD", "TZS", "UGX", "UZS", "VUV", "WST", "XAF", "XCD", "XDR",
        "XOF", "XPF", "YER", "ZMW", "ZWL"
    );

    @Override
    public void initialize(ValidCurrency constraintAnnotation) {
    }

    @Override
    public boolean isValid(String currency, ConstraintValidatorContext context) {
        if (currency == null || currency.trim().isEmpty()) {
            return true;
        }
        
        return VALID_CURRENCIES.contains(currency.toUpperCase());
    }
}
