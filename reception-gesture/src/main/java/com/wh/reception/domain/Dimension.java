package com.wh.reception.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "dimensions")

public class Dimension implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String label;
    private int width;
    private int length;
    

    public Dimension() {
    }

    public Dimension(String label, int width, int length) {
        this.label = label;
        this.width = width;
        this.length = length;
    }

    @Id
    @Column(name = "dimension_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "The dimension name cannot be null")
    @Size(min = 3, max = 50, message = "The name must be between 3 and 50 characters")
    @Column(name = "label", nullable = false, length = 50)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Min(value = 1, message = "The width must be positive")
    @Column(name = "width", nullable = false)
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Min(value = 1, message = "The length must be positive")
    @Column(name = "length", nullable = false)
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    

    // Validation des données
    public void validate() {
        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("The dimension label cannot be null or empty.");
        }
        if (label.length() < 3 || label.length() > 50) {
            throw new IllegalArgumentException("The label must be between 3 and 50 characters.");
        }
        if (width < 1) {
            throw new IllegalArgumentException("The width must be positive and be greater than 1.");
        }
        if (length <= width) {
            throw new IllegalArgumentException("The length must be greater than the width.");
        }
    }

    @Override
    public String toString() {
        return "Dimension [id=" + id + ", label=" + label + ", width=" + width + ", length=" + length + "]";
    }
}