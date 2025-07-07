package com.wh.reception.dto.update;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for updating Category entity
 * Contains only fields that can be updated
 */
public class CategoryUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 50, message = "The label must be between 3 and 50 characters")
    @JsonProperty("label")
    private String label;

    @JsonProperty("description")
    private String description;

    public CategoryUpdateDTO() {
    }

    public CategoryUpdateDTO(String label, String description) {
        this.label = label;
        this.description = description;
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

    /**
     * Check if this DTO has any non-null values to update
     * @return true if at least one field is not null
     */
    public boolean hasUpdates() {
        return label != null || description != null;
    }

    @Override
    public String toString() {
        return "CategoryUpdateDTO [label=" + label + ", description=" + description + "]";
    }
}