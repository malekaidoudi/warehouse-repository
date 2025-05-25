package com.wh.reception.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class ItemLineParcelPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long itemId;
    private Long parcelId;

    public ItemLineParcelPK() {
    }

    public ItemLineParcelPK(Long itemId, Long parcelId) {
        this.itemId = itemId;
        this.parcelId = parcelId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemLineParcelPK that = (ItemLineParcelPK) o;
        return Objects.equals(itemId, that.itemId) && Objects.equals(parcelId, that.parcelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, parcelId);
    }
}