package com.wh.reception.dto.update;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;

/**
 * Data Transfer Object for updating ItemLineParcel entity
 * Contains only fields that can be updated
 */
public class ItemLineParcelUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(value = 1, message = "Quantity must be at least 1")
    @JsonProperty("quantity")
    private Integer quantity;

    public ItemLineParcelUpdateDTO() {
    }

    public ItemLineParcelUpdateDTO(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Check if this DTO has any non-null values to update
     * @return true if at least one field is not null
     */
    public boolean hasUpdates() {
        return quantity != null;
    }

    @Override
    public String toString() {
        return "ItemLineParcelUpdateDTO [quantity=" + quantity + "]";
    }
}