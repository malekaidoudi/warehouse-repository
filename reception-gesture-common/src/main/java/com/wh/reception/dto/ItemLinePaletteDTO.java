package com.wh.reception.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for ItemLinePalette entity
 */
public class ItemLinePaletteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Item ID cannot be null")
    @JsonProperty("itemId")
    private Long itemId;

    @NotNull(message = "Palette ID cannot be null")
    @JsonProperty("paletteId")
    private Long paletteId;

    @Min(value = 1, message = "Quantity must be at least 1")
    @JsonProperty("quantity")
    private Integer quantity;

    // Optional: Include item and palette details for display purposes
    @JsonProperty("item")
    private ItemDTO item;

    @JsonProperty("palette")
    private PaletteDTO palette;

    public ItemLinePaletteDTO() {
    }

    public ItemLinePaletteDTO(Long itemId, Long paletteId, Integer quantity) {
        this.itemId = itemId;
        this.paletteId = paletteId;
        this.quantity = quantity;
    }

    public ItemLinePaletteDTO(Long itemId, Long paletteId, Integer quantity, ItemDTO item, PaletteDTO palette) {
        this.itemId = itemId;
        this.paletteId = paletteId;
        this.quantity = quantity;
        this.item = item;
        this.palette = palette;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getPaletteId() {
        return paletteId;
    }

    public void setPaletteId(Long paletteId) {
        this.paletteId = paletteId;
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

    public PaletteDTO getPalette() {
        return palette;
    }

    public void setPalette(PaletteDTO palette) {
        this.palette = palette;
    }

    @Override
    public String toString() {
        return "ItemLinePaletteDTO [itemId=" + itemId + ", paletteId=" + paletteId + ", quantity=" + quantity + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemLinePaletteDTO that = (ItemLinePaletteDTO) obj;
        return itemId != null && itemId.equals(that.itemId) && 
               paletteId != null && paletteId.equals(that.paletteId);
    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (paletteId != null ? paletteId.hashCode() : 0);
        return result;
    }
}