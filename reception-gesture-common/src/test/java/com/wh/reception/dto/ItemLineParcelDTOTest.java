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

class ItemLineParcelDTOTest {

    @Test
    @DisplayName("Valid ItemLineParcelDTO should pass validation")
    void testValidItemLineParcelDTO() {
        ItemLineParcelDTO itemLineParcel = new ItemLineParcelDTO(1L, 2L, 5);
        
        Set<ConstraintViolation<ItemLineParcelDTO>> violations = ValidationUtils.validate(itemLineParcel);
        assertTrue(violations.isEmpty(), "Valid ItemLineParcelDTO should have no violations");
    }

    @Test
    @DisplayName("ItemLineParcelDTO with null itemId should fail validation")
    void testNullItemId() {
        ItemLineParcelDTO itemLineParcel = new ItemLineParcelDTO(null, 2L, 5);
        
        Set<ConstraintViolation<ItemLineParcelDTO>> violations = ValidationUtils.validate(itemLineParcel);
        assertFalse(violations.isEmpty(), "ItemLineParcelDTO with null itemId should have violations");
        
        String errorMessage = ValidationUtils.getValidationErrors(itemLineParcel);
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("Item ID cannot be null"));
    }

    @Test
    @DisplayName("ItemLineParcelDTO with null parcelId should fail validation")
    void testNullParcelId() {
        ItemLineParcelDTO itemLineParcel = new ItemLineParcelDTO(1L, null, 5);
        
        Set<ConstraintViolation<ItemLineParcelDTO>> violations = ValidationUtils.validate(itemLineParcel);
        assertFalse(violations.isEmpty(), "ItemLineParcelDTO with null parcelId should have violations");
        
        String errorMessage = ValidationUtils.getValidationErrors(itemLineParcel);
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("Parcel ID cannot be null"));
    }

    @Test
    @DisplayName("ItemLineParcelDTO with zero quantity should fail validation")
    void testZeroQuantity() {
        ItemLineParcelDTO itemLineParcel = new ItemLineParcelDTO(1L, 2L, 0);
        
        Set<ConstraintViolation<ItemLineParcelDTO>> violations = ValidationUtils.validate(itemLineParcel);
        assertFalse(violations.isEmpty(), "ItemLineParcelDTO with zero quantity should have violations");
        
        String errorMessage = ValidationUtils.getValidationErrors(itemLineParcel);
        assertNotNull(errorMessage);
        assertTrue(errorMessage.contains("Quantity must be at least 1"));
    }

    @Test
    @DisplayName("ItemLineParcelDTO equality should work correctly")
    void testEquality() {
        ItemLineParcelDTO itemLineParcel1 = new ItemLineParcelDTO(1L, 2L, 5);
        ItemLineParcelDTO itemLineParcel2 = new ItemLineParcelDTO(1L, 2L, 10); // Different quantity
        ItemLineParcelDTO itemLineParcel3 = new ItemLineParcelDTO(2L, 2L, 5); // Different itemId
        
        assertEquals(itemLineParcel1, itemLineParcel2, "ItemLineParcels with same itemId and parcelId should be equal");
        assertFalse(itemLineParcel1.equals(itemLineParcel3), "ItemLineParcels with different itemId should not be equal");
        assertEquals(itemLineParcel1.hashCode(), itemLineParcel2.hashCode(), "Equal ItemLineParcels should have same hash code");
    }

    @Test
    @DisplayName("ItemLineParcelDTO toString should work correctly")
    void testToString() {
        ItemLineParcelDTO itemLineParcel = new ItemLineParcelDTO(1L, 2L, 5);
        String toString = itemLineParcel.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("2"));
        assertTrue(toString.contains("5"));
    }
}