package com.wh.reception.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for ItemLineParcel entity
 */
public class ItemLineParcelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Item ID cannot be null")
    @JsonProperty("itemId")
    private Long itemId;

    @NotNull(message = "Parcel ID cannot be null")
    @JsonProperty("parcelId")
    private Long parcelId;

    @Min(value = 1, message = "Quantity must be at least 1")
    @JsonProperty("quantity")
    private Integer quantity;

    // Optional: Include item and parcel details for display purposes
    @JsonProperty("item")
    private ItemDTO item;

    @JsonProperty("parcel")
    private ParcelDTO parcel;

    public ItemLineParcelDTO() {
    }

    public ItemLineParcelDTO(Long itemId, Long parcelId, Integer quantity) {
        this.itemId = itemId;
        this.parcelId = parcelId;
        this.quantity = quantity;
    }

    public ItemLineParcelDTO(Long itemId, Long parcelId, Integer quantity, ItemDTO item, ParcelDTO parcel) {
        this.itemId = itemId;
        this.parcelId = parcelId;
        this.quantity = quantity;
        this.item = item;
        this.parcel = parcel;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getParcelId() {
        return parcelId;
    }

    public void setParcelId(Long parcelId) {
        this.parcelId = parcelId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public ParcelDTO getParcel() {
        return parcel;
    }

    public void setParcel(ParcelDTO parcel) {
        this.parcel = parcel;
    }

    @Override
    public String toString() {
        return "ItemLineParcelDTO [itemId=" + itemId + ", parcelId=" + parcelId + ", quantity=" + quantity + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemLineParcelDTO that = (ItemLineParcelDTO) obj;
        return itemId != null && itemId.equals(that.itemId) && 
               parcelId != null && parcelId.equals(that.parcelId);
    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (parcelId != null ? parcelId.hashCode() : 0);
        return result;
    }
}