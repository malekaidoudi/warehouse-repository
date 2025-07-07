package com.wh.reception.dto.update;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for updating Item entity
 * Contains only fields that can be updated
 */
public class ItemUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 50, message = "Label must not exceed 50 characters")
    @JsonProperty("label")
    private String label;

    @JsonProperty("description")
    private String description;

    @Positive(message = "Weight must be positive")
    @JsonProperty("weight")
    private Double weight;

    public ItemUpdateDTO() {
    }

    public ItemUpdateDTO(String label, String description, Double weight) {
        this.label = label;
        this.description = description;
        this.weight = weight;
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

    /**
     * Check if this DTO has any non-null values to update
     * @return true if at least one field is not null
     */
    public boolean hasUpdates() {
        return label != null || description != null || weight != null;
    }

    @Override
    public String toString() {
        return "ItemUpdateDTO [label=" + label + ", description=" + description + ", weight=" + weight + "]";
    }
}