package com.wh.reception.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object for Palette entity
 */
public class PaletteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;

    @NotNull(message = "Reception ID cannot be null")
    @JsonProperty("receptionId")
    private Long receptionId;

    @JsonProperty("categoryId")
    private Long categoryId;

    @JsonProperty("dimensionId")
    private Long dimensionId;

    @Positive(message = "Height must be positive")
    @JsonProperty("height")
    private Double height;

    @Positive(message = "Weight must be positive")
    @JsonProperty("weight")
    private Double weight;

    @JsonProperty("stackable")
    private Boolean stackable = false;

    @JsonProperty("fragile")
    private Boolean fragile = false;

    @JsonProperty("rotten")
    private Boolean rotten = false;

    @NotNull(message = "The delivery date cannot be null")
    @JsonProperty("deliveryDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deliveryDate;

    @JsonProperty("expirationDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    @JsonProperty("createdAt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createdAt;

    @JsonProperty("updatedAt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date updatedAt;

    // Optional: Include related entities for display purposes
    @JsonProperty("category")
    private CategoryDTO category;

    @JsonProperty("dimension")
    private DimensionDTO dimension;

    // Optional: Include related entities for display purposes
    @JsonProperty("itemLinePalettes")
    private List<ItemLinePaletteDTO> itemLinePalettes;

    public PaletteDTO() {
    }

    public PaletteDTO(Long receptionId, Long categoryId, Long dimensionId, Double height, Double weight,
                     Boolean stackable, Boolean fragile, Boolean rotten, LocalDate deliveryDate, LocalDate expirationDate) {
        this.receptionId = receptionId;
        this.categoryId = categoryId;
        this.dimensionId = dimensionId;
        this.height = height;
        this.weight = weight;
        this.stackable = stackable;
        this.fragile = fragile;
        this.rotten = rotten;
        this.deliveryDate = deliveryDate;
        this.expirationDate = expirationDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReceptionId() {
        return receptionId;
    }

    public void setReceptionId(Long receptionId) {
        this.receptionId = receptionId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(Long dimensionId) {
        this.dimensionId = dimensionId;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Boolean getStackable() {
        return stackable;
    }

    public void setStackable(Boolean stackable) {
        this.stackable = stackable;
    }

    public Boolean getFragile() {
        return fragile;
    }

    public void setFragile(Boolean fragile) {
        this.fragile = fragile;
    }

    public Boolean getRotten() {
        return rotten;
    }

    public void setRotten(Boolean rotten) {
        this.rotten = rotten;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public DimensionDTO getDimension() {
        return dimension;
    }

    public void setDimension(DimensionDTO dimension) {
        this.dimension = dimension;
    }

    public List<ItemLinePaletteDTO> getItemLinePalettes() {
        return itemLinePalettes;
    }

    public void setItemLinePalettes(List<ItemLinePaletteDTO> itemLinePalettes) {
        this.itemLinePalettes = itemLinePalettes;
    }

    @Override
    public String toString() {
        return "PaletteDTO [id=" + id + ", receptionId=" + receptionId + ", height=" + height + 
               ", weight=" + weight + ", stackable=" + stackable + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PaletteDTO that = (PaletteDTO) obj;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}