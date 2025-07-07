package com.wh.reception.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.wh.reception.utils.ValidationUtils;

import jakarta.validation.ConstraintViolation;

class ItemDTOTest {

    @Test
    @DisplayName("Valid ItemDTO should pass validation")
    void testValidItemDTO() {
        ItemDTO item = new ItemDTO("Smartphone X1", "Smartphone 5G avec écran OLED", 0.2);
        
        Set<ConstraintViolation<ItemDTO>> violations = ValidationUtils.validate(item);
        assertTrue(violations.isEmpty(), "Valid ItemDTO should have no violations");
    }

    @Test
    @DisplayName("ItemDTO with null label should fail validation")
    void testNullLabel() {
        ItemDTO item = new ItemDTO(null, "Description", 1.0);
        
        Set<ConstraintViolation<ItemDTO>> violations = ValidationUtils.validate(item);
        assertFalse(violations.isEmpty(), "ItemDTO with null label should have violations");
        
        String errorMessage = ValidationUtils.getValidationErrors(item);
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("Label cannot be null"));
    }

    @Test
    @DisplayName("ItemDTO with long label should fail validation")
    void testLongLabel() {
        String longLabel = "A".repeat(51);
        ItemDTO item = new ItemDTO(longLabel, "Description", 1.0);
        
        Set<ConstraintViolation<ItemDTO>> violations = ValidationUtils.validate(item);
        assertFalse(violations.isEmpty(), "ItemDTO with long label should have violations");
        
        String errorMessage = ValidationUtils.getValidationErrors(item);
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("Label must not exceed 50 characters"));
    }

    @Test
    @DisplayName("ItemDTO with null description should fail validation")
    void testNullDescription() {
        ItemDTO item = new ItemDTO("Test Item", null, 1.0);
        
        Set<ConstraintViolation<ItemDTO>> violations = ValidationUtils.validate(item);
        assertFalse(violations.isEmpty(), "ItemDTO with null description should have violations");
        
        String errorMessage = ValidationUtils.getValidationErrors(item);
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("Description cannot be null"));
    }

    @Test
    @DisplayName("ItemDTO with negative weight should fail validation")
    void testNegativeWeight() {
        ItemDTO item = new ItemDTO("Test Item", "Description", -1.0);
        
        Set<ConstraintViolation<ItemDTO>> violations = ValidationUtils.validate(item);
        assertFalse(violations.isEmpty(), "ItemDTO with negative weight should have violations");
        
        String errorMessage = ValidationUtils.getValidationErrors(item);
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("Weight must be positive"));
    }

    @Test
    @DisplayName("ItemDTO equality should work correctly")
    void testEquality() {
        ItemDTO item1 = new ItemDTO(1L, "Test Item", "Description", 1.0);
        ItemDTO item2 = new ItemDTO(1L, "Different Label", "Different description", 2.0);
        ItemDTO item3 = new ItemDTO(2L, "Test Item", "Description", 1.0);
        
        assertEquals(item1, item2, "Items with same ID should be equal");
        assertFalse(item1.equals(item3), "Items with different IDs should not be equal");
        assertEquals(item1.hashCode(), item2.hashCode(), "Equal items should have same hash code");
    }

    @Test
    @DisplayName("ItemDTO toString should work correctly")
    void testToString() {
        ItemDTO item = new ItemDTO(1L, "Test Item", "Description", 1.0);
        String toString = item.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("Test Item"));
        assertTrue(toString.contains("Description"));
        assertTrue(toString.contains("1.0"));
        assertTrue(toString.contains("1"));
    }
}