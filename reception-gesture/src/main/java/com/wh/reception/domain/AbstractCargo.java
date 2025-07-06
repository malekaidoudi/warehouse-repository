package com.wh.reception.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import com.wh.reception.exception.InvalidDataException;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AbstractCargo implements Serializable {

    private static final long serialVersionUID = 1L;

    protected double weight;
    protected boolean fragile = false;
    protected boolean rotten = false;
    protected LocalDate deliveryDate;
    protected LocalDate expirationDate;
    protected Date createdAt;
    protected Date updatedAt;
    protected Reception reception;

    @Column(nullable = false)
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Column(columnDefinition = "boolean default false")
    public boolean isFragile() {
        return fragile;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    @Column(columnDefinition = "boolean default false")
    public boolean isRotten() {
        return rotten;
    }

    public void setRotten(boolean rotten) {
        this.rotten = rotten;
    }

    @NotNull(message = "The delivery date cannot be null")
    @Column(name = "delivery_date", nullable = false)
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Column(name = "expiration_date")
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name = "reception_id", nullable = false)
    public Reception getReception() {
        return reception;
    }

    public void setReception(Reception reception) {
        this.reception = reception;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        if (this.updatedAt == null) {
            this.updatedAt = this.createdAt;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    public void validate() {
        LocalDate now = LocalDate.now();
        if ((deliveryDate == null)||expirationDate == null) {
            throw new InvalidDataException("The delivery and expiration dates are mandatory.");
        }
        if (deliveryDate.isBefore(now)) {
            throw new InvalidDataException("The delivery date must be on or after the current date.");
        }
        if(expirationDate != null && expirationDate.isBefore(now)) {
			throw new InvalidDataException("The expiration date must be on or after the current date.");
		}
        if (expirationDate != null && expirationDate.isBefore(deliveryDate)) {
            throw new InvalidDataException("The expiration date must be after the delivery date.");
        }
    }
}