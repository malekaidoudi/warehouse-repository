package com.wh.reception.utils;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Utility class for validation operations
 */
public final class ValidationUtils {

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    private ValidationUtils() {
        // Utility class - prevent instantiation
    }

    /**
     * Validate an object using Bean Validation
     * @param object the object to validate
     * @param <T> the type of the object
     * @return Set of constraint violations
     */
    public static <T> Set<ConstraintViolation<T>> validate(T object) {
        return validator.validate(object);
    }

    /**
     * Check if an object is valid (has no constraint violations)
     * @param object the object to validate
     * @param <T> the type of the object
     * @return true if valid, false otherwise
     */
    public static <T> boolean isValid(T object) {
        return validate(object).isEmpty();
    }

    /**
     * Get validation error messages as a single string
     * @param object the object to validate
     * @param <T> the type of the object
     * @return concatenated error messages
     */
    public static <T> String getValidationErrors(T object) {
        Set<ConstraintViolation<T>> violations = validate(object);
        if (violations.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<T> violation : violations) {
            if (sb.length() > 0) {
                sb.append("; ");
            }
            sb.append(violation.getMessage());
        }
        return sb.toString();
    }

    /**
     * Validate date constraints for cargo items
     * @param deliveryDate the delivery date
     * @param expirationDate the expiration date
     * @throws IllegalArgumentException if dates are invalid
     */
    public static void validateCargodates(LocalDate deliveryDate, LocalDate expirationDate) {
        LocalDate now = LocalDate.now();
        
        if (deliveryDate == null || expirationDate == null) {
            throw new IllegalArgumentException("The delivery and expiration dates are mandatory.");
        }
        
        if (deliveryDate.isBefore(now)) {
            throw new IllegalArgumentException("The delivery date must be on or after the current date.");
        }
        
        if (expirationDate.isBefore(now)) {
            throw new IllegalArgumentException("The expiration date must be on or after the current date.");
        }
        
        if (expirationDate.isBefore(deliveryDate)) {
            throw new IllegalArgumentException("The expiration date must be after the delivery date.");
        }
    }

    /**
     * Check if a string is null or empty (after trimming)
     * @param str the string to check
     * @return true if null or empty
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Check if a string has a valid length
     * @param str the string to check
     * @param minLength minimum length
     * @param maxLength maximum length
     * @return true if length is valid
     */
    public static boolean isValidLength(String str, int minLength, int maxLength) {
        if (str == null) {
            return false;
        }
        int length = str.trim().length();
        return length >= minLength && length <= maxLength;
    }
}