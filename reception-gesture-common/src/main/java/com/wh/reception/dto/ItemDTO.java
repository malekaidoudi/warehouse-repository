package com.wh.reception.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Item entity
 */
public class ItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;

    @NotNull(message = "Label cannot be null")
    @Size(max = 50, message = "Label must not exceed 50 characters")
    @JsonProperty("label")
    private String label;

    @NotNull(message = "Description cannot be null")
    @JsonProperty("description")
    private String description;

    @NotNull(message = "Weight cannot be null")
    @Positive(message = "Weight must be positive")
    @JsonProperty("weight")
    private Double weight;

    public ItemDTO() {
    }

    public ItemDTO(String label, String description, Double weight) {
        this.label = label;
        this.description = description;
        this.weight = weight;
    }

    public ItemDTO(Long id, String label, String description, Double weight) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "ItemDTO [id=" + id + ", label=" + label + ", description=" + description + ", weight=" + weight + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemDTO that = (ItemDTO) obj;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}