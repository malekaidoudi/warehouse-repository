package com.wh.reception.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String label;
    private String description;

    public Category() {
    }

    public Category(String label, String description) {
        this.label = label;
        this.description = description;
    }

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "The category label cannot be null")
    @Size(min = 3, max = 50, message = "The label must be between 3 and 50 characters")
    @Column(name = "label", nullable = false, length = 50)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Validation des données
    public void validate() {
        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("The category label cannot be empty.");
        }
        if (label.length() < 3 || label.length() > 50) {
            throw new IllegalArgumentException("The category label must be between 3 and 50 characters.");
        }
        if (description.length() < 3 || description.length() > 50) {
            throw new IllegalArgumentException("The category description must be between 3 and 50 characters.");
        }
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", label=" + label + "]";
    }
}