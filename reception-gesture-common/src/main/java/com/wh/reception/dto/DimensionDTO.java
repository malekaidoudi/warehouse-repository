package com.wh.reception.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Dimension entity
 */
public class DimensionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;

    @NotNull(message = "The dimension name cannot be null")
    @Size(min = 3, max = 50, message = "The label must be between 3 and 50 characters")
    @JsonProperty("label")
    private String label;

    @Min(value = 1, message = "The width must be positive")
    @JsonProperty("width")
    private Double width;

    @Min(value = 1, message = "The length must be positive")
    @JsonProperty("length")
    private Double length;

    public DimensionDTO() {
    }

    public DimensionDTO(String label, Double width, Double length) {
        this.label = label;
        this.width = width;
        this.length = length;
    }

    public DimensionDTO(Long id, String label, Double width, Double length) {
        this.id = id;
        this.label = label;
        this.width = width;
        this.length = length;
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

    @Override
    public String toString() {
        return "DimensionDTO [id=" + id + ", label=" + label + ", width=" + width + ", length=" + length + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DimensionDTO that = (DimensionDTO) obj;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}