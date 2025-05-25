package com.wh.reception.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "item_line_parcel")
public class ItemLineParcel implements Serializable {

    private static final long serialVersionUID = 1L;

    private ItemLineParcelPK id;
    private int quantity;
    private Item item;
    private Parcel parcel;

    public ItemLineParcel() {
        this.id = new ItemLineParcelPK();
    }

    public ItemLineParcel(int quantity) {
        this.id = new ItemLineParcelPK();
        this.quantity = quantity;
    }

    @EmbeddedId
    public ItemLineParcelPK getId() {
        return id;
    }

    public void setId(ItemLineParcelPK id) {
        this.id = id;
    }

    @Column(nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @ManyToOne
    @MapsId("parcelId")
    @JoinColumn(name = "parcel_id")
    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    @Override
    public String toString() {
        return "ItemLineParcel [item=" + item + ", parcel=" + parcel + ", quantity=" + quantity + "]";
    }
}