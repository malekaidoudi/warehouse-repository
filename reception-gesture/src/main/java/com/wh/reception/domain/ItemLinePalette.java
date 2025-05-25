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
@Table(name = "item_line_palette")
public class ItemLinePalette implements Serializable {

    private static final long serialVersionUID = 1L;

    private ItemLinePalettePK id;
    private int quantity;
    private Item item;
    private Palette palette;

    public ItemLinePalette() {
        this.id = new ItemLinePalettePK();
    }

    public ItemLinePalette(int quantity) {
        this.id = new ItemLinePalettePK();
        this.quantity = quantity;
    }

    @EmbeddedId
    public ItemLinePalettePK getId() {
        return id;
    }

    public void setId(ItemLinePalettePK id) {
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
    @MapsId("paletteId")
    @JoinColumn(name = "palette_id")
    public Palette getPalette() {
        return palette;
    }

    public void setPalette(Palette palette) {
        this.palette = palette;
    }

    @Override
    public String toString() {
        return "ItemLinePalette [item=" + item + ", palette=" + palette + ", quantity=" + quantity + "]";
    }
}