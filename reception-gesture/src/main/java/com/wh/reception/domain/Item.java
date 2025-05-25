package com.wh.reception.domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "items")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String label;
    private String description;
    private double weight;
    private List<ItemLineParcel> itemLineParcels;
    private List<ItemLinePalette> itemLinePalettes;

    public Item() {
    }

    public Item(String label, String description, double weight) {
        this.label = label;
        this.description = description;
        this.weight = weight;
    }

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "Label cannot be null")
    @Size(max = 50, message = "Label must not exceed 50 characters")
    @Column(name = "label", nullable = false, length = 50)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @NotNull(message = "Description cannot be null")
    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Weight cannot be null")
    @Positive(message = "Weight must be positive")
    @Column(name = "weight", nullable = false)
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    public List<ItemLineParcel> getItemLineParcels() {
        return itemLineParcels;
    }

    public void setItemLineParcels(List<ItemLineParcel> itemLineParcels) {
        this.itemLineParcels = itemLineParcels;
    }

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    public List<ItemLinePalette> getItemLinePalettes() {
        return itemLinePalettes;
    }

    public void setItemLinePalettes(List<ItemLinePalette> itemLinePalettes) {
        this.itemLinePalettes = itemLinePalettes;
    }

    public void validate() {
        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("Item label cannot be empty or null.");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Item description cannot be empty or null.");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive.");
        }
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", label=" + label + ", description=" + description + "]";
    }
}