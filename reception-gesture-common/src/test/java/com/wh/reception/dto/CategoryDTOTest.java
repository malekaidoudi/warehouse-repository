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

class CategoryDTOTest {

    @Test
    @DisplayName("Valid CategoryDTO should pass validation")
    void testValidCategoryDTO() {
        CategoryDTO category = new CategoryDTO("Electronics", "Electronic devices and components");
        
        Set<ConstraintViolation<CategoryDTO>> violations = ValidationUtils.validate(category);
        assertTrue(violations.isEmpty(), "Valid CategoryDTO should have no violations");
    }

    @Test
    @DisplayName("CategoryDTO with null label should fail validation")
    void testNullLabel() {
        CategoryDTO category = new CategoryDTO(null, "Description");
        
        Set<ConstraintViolation<CategoryDTO>> violations = ValidationUtils.validate(category);
        assertFalse(violations.isEmpty(), "CategoryDTO with null label should have violations");
        
        String errorMessage = ValidationUtils.getValidationErrors(category);
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("category label cannot be null"));
    }

    @Test
    @DisplayName("CategoryDTO with short label should fail validation")
    void testShortLabel() {
        CategoryDTO category = new CategoryDTO("AB", "Description");
        
        Set<ConstraintViolation<CategoryDTO>> violations = ValidationUtils.validate(category);
        assertFalse(violations.isEmpty(), "CategoryDTO with short label should have violations");
        
        String errorMessage = ValidationUtils.getValidationErrors(category);
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("must be between 3 and 50 characters"));
    }

    @Test
    @DisplayName("CategoryDTO with long label should fail validation")
    void testLongLabel() {
        String longLabel = "A".repeat(51);
        CategoryDTO category = new CategoryDTO(longLabel, "Description");
        
        Set<ConstraintViolation<CategoryDTO>> violations = ValidationUtils.validate(category);
        assertFalse(violations.isEmpty(), "CategoryDTO with long label should have violations");
        
        String errorMessage = ValidationUtils.getValidationErrors(category);
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("must be between 3 and 50 characters"));
    }

    @Test
    @DisplayName("CategoryDTO equality should work correctly")
    void testEquality() {
        CategoryDTO category1 = new CategoryDTO(1L, "Electronics", "Electronic devices");
        CategoryDTO category2 = new CategoryDTO(1L, "Different Label", "Different description");
        CategoryDTO category3 = new CategoryDTO(2L, "Electronics", "Electronic devices");
        
        assertEquals(category1, category2, "Categories with same ID should be equal");
        assertFalse(category1.equals(category3), "Categories with different IDs should not be equal");
        assertEquals(category1.hashCode(), category2.hashCode(), "Equal categories should have same hash code");
    }

    @Test
    @DisplayName("CategoryDTO toString should work correctly")
    void testToString() {
        CategoryDTO category = new CategoryDTO(1L, "Electronics", "Electronic devices");
        String toString = category.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("Electronics"));
        assertTrue(toString.contains("Electronic devices"));
        assertTrue(toString.contains("1"));
    }
}