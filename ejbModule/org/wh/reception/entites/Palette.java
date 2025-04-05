package org.wh.reception.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity 
@Table(name="palettes")
@PrimaryKeyJoinColumn( name = "reference" )
public class Palette extends Reception{
	private static final long serialVersionUID = 7883738844966995584L;
	@Column(columnDefinition = "boolean default false")
	private boolean stackable=false;
	@Column(nullable = false)
    private int height;

	@ManyToOne
    @JoinColumn(name="dimension_id")
    private Dimension dimension;

	@ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    
	   public Palette() {
		}

	    public Palette( boolean stackable, int height, Dimension dimension, Category category) {
			this.stackable = stackable;
			this.height = height;
			this.dimension = dimension;
			this.category = category;
		}

	public boolean getStackable() {
		return stackable;
	}

	public void setStackable(Boolean stackable) {
		this.stackable = stackable;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

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
