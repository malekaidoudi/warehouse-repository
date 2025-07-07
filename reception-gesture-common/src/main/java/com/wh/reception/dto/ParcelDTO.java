package com.wh.reception.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object for Parcel entity
 */
public class ParcelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;

    @NotNull(message = "Reception ID cannot be null")
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

    public ParcelDTO() {
    }

    public ParcelDTO(Long receptionId, Double width, Double length, Double weight, 
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

    @Override
    public String toString() {
        return "ParcelDTO [id=" + id + ", receptionId=" + receptionId + ", width=" + width + 
               ", length=" + length + ", weight=" + weight + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ParcelDTO that = (ParcelDTO) obj;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}