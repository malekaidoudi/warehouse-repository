package com.wh.reception.dto.update;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object for updating Parcel entity
 * Contains only fields that can be updated
 */
public class ParcelUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("receptionId")
    private Long receptionId;

    @Positive(message = "Width must be positive")
    @JsonProperty("width")
    private Double width;

    @Positive(message = "Length must be positive")
    @JsonProperty("length")
    private Double length;

    @Positive(message = "Weight must be positive")
    @JsonProperty("weight")
    private Double weight;

    @JsonProperty("fragile")
    private Boolean fragile;

    @JsonProperty("rotten")
    private Boolean rotten;

    @JsonProperty("deliveryDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deliveryDate;

    @JsonProperty("expirationDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    public ParcelUpdateDTO() {
    }

    public ParcelUpdateDTO(Long receptionId, Double width, Double length, Double weight, 
                          Boolean fragile, Boolean rotten, LocalDate deliveryDate, LocalDate expirationDate) {
        this.receptionId = receptionId;
        this.width = width;
        this.length = length;
        this.weight = weight;
        this.fragile = fragile;
        this.rotten = rotten;
        this.deliveryDate = deliveryDate;
        this.expirationDate = expirationDate;
    }

    // Getters and Setters
    public Long getReceptionId() {
        return receptionId;
    }

    public void setReceptionId(Long receptionId) {
        this.receptionId = receptionId;
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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
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

    /**
     * Check if this DTO has any non-null values to update
     * @return true if at least one field is not null
     */
    public boolean hasUpdates() {
        return receptionId != null || width != null || length != null || weight != null ||
               fragile != null || rotten != null || deliveryDate != null || expirationDate != null;
    }

    @Override
    public String toString() {
        return "ParcelUpdateDTO [receptionId=" + receptionId + ", width=" + width + 
               ", length=" + length + ", weight=" + weight + "]";
    }
}