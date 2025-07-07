package com.wh.reception.dto.update;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for updating Dimension entity
 * Contains only fields that can be updated
 */
public class DimensionUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 50, message = "The label must be between 3 and 50 characters")
    @JsonProperty("label")
    private String label;

    @Min(value = 1, message = "The width must be positive")
    @JsonProperty("width")
    private Double width;

    @Min(value = 1, message = "The length must be positive")
    @JsonProperty("length")
    private Double length;

    public DimensionUpdateDTO() {
    }

    public DimensionUpdateDTO(String label, Double width, Double length) {
        this.label = label;
        this.width = width;
        this.length = length;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    /**
     * Check if this DTO has any non-null values to update
     * @return true if at least one field is not null
     */
    public boolean hasUpdates() {
        return label != null || width != null || length != null;
    }

    @Override
    public String toString() {
        return "DimensionUpdateDTO [label=" + label + ", width=" + width + ", length=" + length + "]";
    }
}