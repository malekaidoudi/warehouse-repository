package com.wh.reception.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity 
@Table(name="palettes")
@PrimaryKeyJoinColumn( name = "reference" )
public class Palette extends Reception{
	
	private static final long serialVersionUID = 1L;
	private boolean stackable=false;
    private int height;
    private Dimension dimension;
    private Category category;
    
	   public Palette() {
		}

	    public Palette( boolean stackable, int height, Dimension dimension, Category category) {
	    	super();
			this.stackable = stackable;
			this.height = height;
			this.dimension = dimension;
			this.category = category;
		}

	@Column(columnDefinition = "boolean default false")
	public boolean getStackable() {
		return stackable;
	}

	public void setStackable(Boolean stackable) {
		this.stackable = stackable;
	}

	@Column(nullable = false)
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	@ManyToOne
    @JoinColumn(name="dimension_id")
	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}
	@ManyToOne
    @JoinColumn(name="category_id")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Palette [Reference = " + getReference() + ",Created at= "+getCreatedAt()+"]";
	}
}
