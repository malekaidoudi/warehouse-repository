package com.wh.reception.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity 
@Table(name="parcels")
@PrimaryKeyJoinColumn( name = "reference" )
public class Parcel extends Reception {
	
	
	
	
	private static final long serialVersionUID = 1L;
	
    private int width;
    private int length;

	public Parcel() {
	}

	public Parcel(int width, int length) {
		this.width = width;
		this.length = length;
	}

	@Column(nullable = false)
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Column(nullable = false)
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
