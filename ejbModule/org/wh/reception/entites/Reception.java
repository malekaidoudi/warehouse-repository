package org.wh.reception.entites;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="receptions")
@Inheritance( strategy = InheritanceType.JOINED)
public abstract class  Reception implements Serializable{
	
	private static final long serialVersionUID = 100532828517285289L;
	
	@Id
	@Column(name = "reception_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	private Long reference;
	@Column(name="shipping_address",nullable = false)
	private String shippingAddress;
	@Column(name="delivery_address",nullable = false)
	private String deliveryAddress;
	@Temporal(TemporalType.DATE)
	@Column(name="delivery_date")
	private Date deliveryDate;
	@Column(nullable = false)
    private double weight;
	@Column(columnDefinition = "boolean default false")
    private boolean fragile=false;
	@Column(columnDefinition = "boolean default false")
    private boolean rotten=false;
    @Temporal(TemporalType.DATE)
    @Column(name="expiration_date")
    private Date expirationDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="updated_at")
    private Date updatedAt;
	
   @OneToMany(mappedBy = "reception")
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
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public List<ItemLineReception> getPackaging() {
		return packaging;
	}

	public void setPackaging(List<ItemLineReception> packaging) {
		this.packaging = packaging;
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
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public boolean getFragile() {
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
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
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
