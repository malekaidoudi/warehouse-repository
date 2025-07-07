package com.wh.reception.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for Reception entity
 */
public class ReceptionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("reference")
    private Long reference;

    @NotNull(message = "Order initiator is mandatory")
    @JsonProperty("orderInitiator")
    private String orderInitiator;

    @NotNull(message = "Shiper is mandatory")
    @JsonProperty("shiper")
    private String shiper;

    @NotNull(message = "Consignee is mandatory")
    @JsonProperty("consignee")
    private String consignee;

    @NotNull(message = "Shipping address is mandatory")
    @JsonProperty("shippingAddress")
    private String shippingAddress;

    @NotNull(message = "Delivery address is mandatory")
    @JsonProperty("deliveryAddress")
    private String deliveryAddress;

    @JsonProperty("createdAt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createdAt;

    @JsonProperty("updatedAt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date updatedAt;

    // Optional: Include related entities for display purposes
    @JsonProperty("parcels")
    private List<ParcelDTO> parcels;

    @JsonProperty("palettes")
    private List<PaletteDTO> palettes;

    // Computed fields
    @JsonProperty("parcelsCount")
    private Integer parcelsCount;

    @JsonProperty("palettesCount")
    private Integer palettesCount;

    public ReceptionDTO() {
    }

    public ReceptionDTO(String orderInitiator, String shiper, String consignee, 
                       String shippingAddress, String deliveryAddress) {
        this.orderInitiator = orderInitiator;
        this.shiper = shiper;
        this.consignee = consignee;
        this.shippingAddress = shippingAddress;
        this.deliveryAddress = deliveryAddress;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public String getOrderInitiator() {
        return orderInitiator;
    }

    public void setOrderInitiator(String orderInitiator) {
        this.orderInitiator = orderInitiator;
    }

    public String getShiper() {
        return shiper;
    }

    public void setShiper(String shiper) {
        this.shiper = shiper;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
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

    public List<ParcelDTO> getParcels() {
        return parcels;
    }

    public void setParcels(List<ParcelDTO> parcels) {
        this.parcels = parcels;
    }

    public List<PaletteDTO> getPalettes() {
        return palettes;
    }

    public void setPalettes(List<PaletteDTO> palettes) {
        this.palettes = palettes;
    }

    public Integer getParcelsCount() {
        return parcelsCount;
    }

    public void setParcelsCount(Integer parcelsCount) {
        this.parcelsCount = parcelsCount;
    }

    public Integer getPalettesCount() {
        return palettesCount;
    }

    public void setPalettesCount(Integer palettesCount) {
        this.palettesCount = palettesCount;
    }

    @Override
    public String toString() {
        return "ReceptionDTO [id=" + id + ", reference=" + reference + ", orderInitiator=" + orderInitiator + 
               ", shiper=" + shiper + ", consignee=" + consignee + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ReceptionDTO that = (ReceptionDTO) obj;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}