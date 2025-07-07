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

class DimensionDTOTest {

    @Test
    @DisplayName("Valid DimensionDTO should pass validation")
    void testValidDimensionDTO() {
        DimensionDTO dimension = new DimensionDTO("Euro Palette", 800.0, 1200.0);
        
        Set<ConstraintViolation<DimensionDTO>> violations = ValidationUtils.validate(dimension);
        assertTrue(violations.isEmpty(), "Valid DimensionDTO should have no violations");
    }

    @Test
    @DisplayName("DimensionDTO with null label should fail validation")
    void testNullLabel() {
        DimensionDTO dimension = new DimensionDTO(null, 800.0, 1200.0);
        
        Set<ConstraintViolation<DimensionDTO>> violations = ValidationUtils.validate(dimension);
        assertFalse(violations.isEmpty(), "DimensionDTO with null label should have violations");
        
        String errorMessage = ValidationUtils.getValidationErrors(dimension);
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("dimension name cannot be null"));
    }

    @Test
    @DisplayName("DimensionDTO with negative width should fail validation")
    void testNegativeWidth() {
        DimensionDTO dimension = new DimensionDTO("Test Dimension", -100.0, 1200.0);
        
        Set<ConstraintViolation<DimensionDTO>> violations = ValidationUtils.validate(dimension);
        assertFalse(violations.isEmpty(), "DimensionDTO with negative width should have violations");
        
        String errorMessage = ValidationUtils.getValidationErrors(dimension);
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("width must be positive"));
    }

    @Test
    @DisplayName("DimensionDTO with zero length should fail validation")
    void testZeroLength() {
        DimensionDTO dimension = new DimensionDTO("Test Dimension", 800.0, 0.0);
        
        Set<ConstraintViolation<DimensionDTO>> violations = ValidationUtils.validate(dimension);
        assertFalse(violations.isEmpty(), "DimensionDTO with zero length should have violations");
        
        String errorMessage = ValidationUtils.getValidationErrors(dimension);
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("length must be positive"));
    }

    @Test
    @DisplayName("DimensionDTO equality should work correctly")
    void testEquality() {
        DimensionDTO dimension1 = new DimensionDTO(1L, "Euro Palette", 800.0, 1200.0);
        DimensionDTO dimension2 = new DimensionDTO(1L, "Different Label", 900.0, 1300.0);
        DimensionDTO dimension3 = new DimensionDTO(2L, "Euro Palette", 800.0, 1200.0);
        
        assertEquals(dimension1, dimension2, "Dimensions with same ID should be equal");
        assertFalse(dimension1.equals(dimension3), "Dimensions with different IDs should not be equal");
        assertEquals(dimension1.hashCode(), dimension2.hashCode(), "Equal dimensions should have same hash code");
    }

    @Test
    @DisplayName("DimensionDTO toString should work correctly")
    void testToString() {
        DimensionDTO dimension = new DimensionDTO(1L, "Euro Palette", 800.0, 1200.0);
        String toString = dimension.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("Euro Palette"));
        assertTrue(toString.contains("800.0"));
        assertTrue(toString.contains("1200.0"));
        assertTrue(toString.contains("1"));
    }
}