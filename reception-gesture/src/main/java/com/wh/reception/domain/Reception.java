package com.wh.reception.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="receptions")
@Inheritance( strategy = InheritanceType.JOINED)
public class Reception implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Long Id;
	private Long reference;
	private String shippingAddress;
	private String deliveryAddress;
	private Date deliveryDate;
    private double weight;
    private boolean fragile=false;
    private boolean rotten=false;
    private Date expirationDate;
    private Date createdAt;
    private Date updatedAt;
	
 
	private List <ItemLineReception> packaging;
    
    public Reception() {
	}
	public Reception(Long reference, Long receptionNumber, String shippingAddress, String deliveryAddress,
			Date deliveryDate, double weight, boolean fragile, boolean rotten, Date expirationDate, Date createdAt,
			Date updatedAt) {
		this.reference = reference;
		this.shippingAddress = shippingAddress;
		this.deliveryAddress = deliveryAddress;
		this.deliveryDate = deliveryDate;
		this.weight = weight;
		this.fragile = fragile;
		this.rotten = rotten;
		this.expirationDate = expirationDate;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	
	@Id @Column(name = "reception_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	 @OneToMany(mappedBy = "reception")
	public List<ItemLineReception> getPackaging() {
		return packaging;
	}

	public void setPackaging(List<ItemLineReception> packaging) {
		this.packaging = packaging;
	}
	
	@Column(name="shipping_address",nullable = false)
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	
	@Column(name="delivery_address",nullable = false)
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="delivery_date")
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	@Column(nullable = false)
	public double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	@Column(columnDefinition = "boolean default false")
	public boolean getFragile() {
		return fragile;
	}
	public void setFragile(Boolean fragile) {
		this.fragile = fragile;
	}
	
	@Column(columnDefinition = "boolean default false")
	public boolean getRotten() {
		return rotten;
	}
	public void setRotten(Boolean rotten) {
		this.rotten = rotten;
	}
	
	@Temporal(TemporalType.DATE)
    @Column(name="expiration_date")
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}
	@Override
	public String toString() {
		return "Reception [Id=" + Id + ", created at=" + createdAt + "]";
	}
}
