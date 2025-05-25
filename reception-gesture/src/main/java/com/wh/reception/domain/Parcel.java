package com.wh.reception.domain;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "parcels")
public class Parcel extends AbstractCargo {

    private static final long serialVersionUID = 1L;

    private Long id;
    private double width; 
    private double length; 
    private List<ItemLineParcel> itemLineParcels;
    

    public Parcel() {
    }

    public Parcel(double width, double length, double weight, boolean fragile, boolean rotten,
                  LocalDate deliveryDate, LocalDate expirationDate, Reception reception) {
        this.width = width;
        this.length = length;
        this.weight = weight;
        this.fragile = fragile;
        this.rotten = rotten;
        this.deliveryDate = deliveryDate;
        this.expirationDate = expirationDate;
        this.reception = reception;
    }

    @Id
    @Column(name = "parcel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Column(nullable = false)
    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
    @OneToMany(mappedBy = "parcel", fetch = FetchType.LAZY)
    public List<ItemLineParcel> getItemLineParcels() {
        return itemLineParcels;
    }

    public void setItemLineParcels(List<ItemLineParcel> itemLineParcels) {
        this.itemLineParcels = itemLineParcels;
    }
   
	@Override
	public String toString() {
		return "Parcel [id=" + id + ", width=" + width + ", length=" + length + "]";
	}


}