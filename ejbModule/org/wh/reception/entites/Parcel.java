package org.wh.reception.entites;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity 
@Table(name="parcels")
@PrimaryKeyJoinColumn( name = "reference" )
public class Parcel extends Reception {

	private static final long serialVersionUID = 3820195148786038863L;
	
	@Column(nullable = false)
    private int width;
	@Column(nullable = false)
    private int length;

	public Parcel() {
	}

	public Parcel(int width, int length) {
		this.width = width;
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	@Override
	public String toString() {
		return "Parcel [Reference = " + getReference() + ",Created at= "+getCreatedAt()+"]";
	}
	
	}
