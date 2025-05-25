package com.wh.reception.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "receptions")
public class Reception implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long reference;
	private String orderInitiator;
	private String shiper;
	private String consignee;
	private String shippingAddress;
	private String deliveryAddress;
	private Date createdAt;
	private Date updatedAt;

	private List<Parcel> parcels = new ArrayList<>();
	private List<Palette> palettes = new ArrayList<>();

	public Reception() {
	}

	public Reception(String orderInitiator, String shiper, String consignee, String shippingAddress,
			String deliveryAddress) {
		super();
		this.orderInitiator = orderInitiator;
		this.shiper = shiper;
		this.consignee = consignee;
		this.shippingAddress = shippingAddress;
		this.deliveryAddress = deliveryAddress;
	}

	@Id
	@Column(name = "reception_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "reference", unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reception_reference_seq")
	@SequenceGenerator(name = "reception_reference_seq", sequenceName = "reception_reference_seq", allocationSize = 1, initialValue = 1000)
	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	@Column(name = "order_initiator", nullable = false, length = 50)
	@NotNull(message = "Order initiator is mandatory")
	public String getOrderInitiator() {
		return orderInitiator;
	}

	public void setOrderInitiator(String orderInitiator) {
		this.orderInitiator = orderInitiator;
	}

	@Column(name = "shiper", nullable = false, length = 50)
	@NotNull(message = "Shiper is mandatory")
	public String getShiper() {
		return shiper;
	}

	public void setShiper(String shiper) {
		this.shiper = shiper;
	}

	@Column(name = "consignee", nullable = false, length = 50)
	@NotNull(message = "Consignee is mandatory")
	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	@Column(name = "shipping_address", nullable = false)
	@NotNull(message = "Shipping address is mandatory")
	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@Column(name = "delivery_address", nullable = false)
	@NotNull(message = "Delivery address is mandatory")
	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	@OneToMany(mappedBy = "reception", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Parcel> getParcels() {
		return parcels;
	}

	public void setParcels(List<Parcel> parcels) {
		this.parcels = parcels;
	}

	@OneToMany(mappedBy = "reception", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Palette> getPalettes() {
		return palettes;
	}

	public void setPalettes(List<Palette> palettes) {
		this.palettes = palettes;
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
	@Column(name = "updated_at", nullable = false)
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	// Retourne le nombre de palettes associées à la réception
	@Transient
	public int getPalettesCount() {
		return palettes != null ? palettes.size() : 0;
	}

	// Retourne le nombre de colis associés à la réception
	@Transient
	public int getParcelsCount() {
		return parcels != null ? parcels.size() : 0;
	}

	// Validation pour s'assurer qu'il y a au moins un colis ou une palette
	public void validate() {
		if ((parcels == null || parcels.isEmpty()) && (palettes == null || palettes.isEmpty())) {
			throw new IllegalStateException("A reception must contain at least one parcel or palette.");
		}
	}

	@Override
	public String toString() {
		return "Reception [id=" + id + ", reference=" + reference + ", shiper=" + shiper + ", consignee=" + consignee
				+ "]";
	}
}