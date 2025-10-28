package com.koshal.webhook.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utility class for HMAC signature validation
 * Supports SHA-256 HMAC validation for webhook authenticity
 */
@Slf4j
@Component
public class HmacSignatureValidator {

    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final String SIGNATURE_PREFIX = "sha256=";

    public boolean isValidSignature(String payload, String signature, String secret) {
        if (payload == null || signature == null || secret == null) {
            log.warn("Invalid parameters for signature validation: payload={}, signature={}, secret={}", 
                    payload != null, signature != null, secret != null);
            return false;
        }

        try {
            String expectedSignature = calculateSignature(payload, secret);
            String providedSignature = extractSignature(signature);
            
            boolean isValid = constantTimeEquals(expectedSignature, providedSignature);
            
            if (!isValid) {
                log.warn("Signature validation failed. Expected: {}, Provided: {}", 
                        expectedSignature, providedSignature);
            }
            
            return isValid;
            
        } catch (Exception e) {
            log.error("Error during signature validation", e);
            return false;
        }
    }

    /**
     * Calculates HMAC-SHA256 signature for the given payload and secret
     */
    public String calculateSignature(String payload, String secret) 
            throws NoSuchAlgorithmException, InvalidKeyException {
        
        Mac mac = Mac.getInstance(HMAC_SHA256);
        SecretKeySpec secretKeySpec = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8), 
                HMAC_SHA256
        );
        mac.init(secretKeySpec);
        
        byte[] signatureBytes = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    /**
     * Extracts the signature value from the signature header
     */
    private String extractSignature(String signatureHeader) {
        if (signatureHeader.startsWith(SIGNATURE_PREFIX)) {
            return signatureHeader.substring(SIGNATURE_PREFIX.length());
        }
        return signatureHeader;
    }

    /**
     * Constant time comparison to prevent timing attacks
     */
    private boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        
        if (a.length() != b.length()) {
            return false;
        }
        
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        
        return result == 0;
    }

    /**
     * Validates timestamp to prevent replay attacks
     */
    public boolean isValidTimestamp(long timestamp, int toleranceSeconds) {
        long currentTime = System.currentTimeMillis() / 1000;
        long timeDifference = Math.abs(currentTime - timestamp);
        
        boolean isValid = timeDifference <= toleranceSeconds;
        
        if (!isValid) {
            log.warn("Timestamp validation failed. Current: {}, Webhook: {}, Difference: {}s, Tolerance: {}s", 
                    currentTime, timestamp, timeDifference, toleranceSeconds);
        }
        
        return isValid;
    }
}
