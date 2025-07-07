package com.wh.reception.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.wh.reception.dto.CategoryDTO;

class ValidationUtilsTest {

    @Test
    @DisplayName("Valid object should pass validation")
    void testValidObject() {
        CategoryDTO category = new CategoryDTO("Electronics", "Electronic devices");
        assertTrue(ValidationUtils.isValid(category));
        assertNull(ValidationUtils.getValidationErrors(category));
    }

    @Test
    @DisplayName("Invalid object should fail validation")
    void testInvalidObject() {
        CategoryDTO category = new CategoryDTO("AB", "Description"); // Too short label
        assertFalse(ValidationUtils.isValid(category));
        
        String errors = ValidationUtils.getValidationErrors(category);
        assertNotNull(errors);
        assertTrue(errors.contains("must be between 3 and 50 characters"));
    }

    @Test
    @DisplayName("Valid cargo dates should pass validation")
    void testValidCargoDate() {
        LocalDate deliveryDate = LocalDate.now().plusDays(1);
        LocalDate expirationDate = LocalDate.now().plusDays(7);
        
        // Should not throw exception
        ValidationUtils.validateCargoDate(deliveryDate, expirationDate);
    }

    @Test
    @DisplayName("Null delivery date should fail validation")
    void testNullDeliveryDate() {
        LocalDate expirationDate = LocalDate.now().plusDays(7);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ValidationUtils.validateCargoDate(null, expirationDate);
        });
        
        assertTrue(exception.getMessage().contains("delivery and expiration dates are mandatory"));
    }

    @Test
    @DisplayName("Past delivery date should fail validation")
    void testPastDeliveryDate() {
        LocalDate deliveryDate = LocalDate.now().minusDays(1);
        LocalDate expirationDate = LocalDate.now().plusDays(7);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ValidationUtils.validateCargoDate(deliveryDate, expirationDate);
        });
        
        assertTrue(exception.getMessage().contains("delivery date must be on or after the current date"));
    }

    @Test
    @DisplayName("Expiration date before delivery date should fail validation")
    void testExpirationBeforeDelivery() {
        LocalDate deliveryDate = LocalDate.now().plusDays(7);
        LocalDate expirationDate = LocalDate.now().plusDays(1);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ValidationUtils.validateCargoDate(deliveryDate, expirationDate);
        });
        
        assertTrue(exception.getMessage().contains("expiration date must be after the delivery date"));
    }

    @Test
    @DisplayName("Null or empty string validation should work correctly")
    void testNullOrEmpty() {
        assertTrue(ValidationUtils.isNullOrEmpty(null));
        assertTrue(ValidationUtils.isNullOrEmpty(""));
        assertTrue(ValidationUtils.isNullOrEmpty("   "));
        assertFalse(ValidationUtils.isNullOrEmpty("test"));
        assertFalse(ValidationUtils.isNullOrEmpty("  test  "));
    }

    @Test
    @DisplayName("String length validation should work correctly")
    void testValidLength() {
        assertFalse(ValidationUtils.isValidLength(null, 3, 10));
        assertFalse(ValidationUtils.isValidLength("AB", 3, 10));
        assertTrue(ValidationUtils.isValidLength("ABC", 3, 10));
        assertTrue(ValidationUtils.isValidLength("ABCDEFGHIJ", 3, 10));
        assertFalse(ValidationUtils.isValidLength("ABCDEFGHIJK", 3, 10));
        assertTrue(ValidationUtils.isValidLength("  ABC  ", 3, 10)); // Should trim
    }
}