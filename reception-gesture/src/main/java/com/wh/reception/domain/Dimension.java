package com.wh.reception.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Dimension implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String labelDimension;
	private double width;
	private double length;
	
	public Dimension() {
	}

	public Dimension(String labelDimension, double width, double length) {
		this.labelDimension = labelDimension;
		this.width = width;
		this.length = length;
	}

	@Id @Column(name="dimension_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="dimension_label",length=30,nullable = false,unique = true)
	public String getLabelDimension() {
		return labelDimension;
	}

	public void setLabelDimension(String labelDimension) {
		this.labelDimension = labelDimension;
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

	@Override
	public String toString() {
		return "Dimension [id=" + id + ", labelDimension=" + labelDimension + "]";
	}	

}
