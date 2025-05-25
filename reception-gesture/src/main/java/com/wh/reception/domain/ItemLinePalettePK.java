package com.wh.reception.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class ItemLinePalettePK implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long itemId;
    private Long paletteId;

    public ItemLinePalettePK() {
    }

    public ItemLinePalettePK(Long itemId, Long paletteId) {
        this.itemId = itemId;
        this.paletteId = paletteId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemLinePalettePK that = (ItemLinePalettePK) o;
        return Objects.equals(itemId, that.itemId) && Objects.equals(paletteId, that.paletteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, paletteId);
    }
}