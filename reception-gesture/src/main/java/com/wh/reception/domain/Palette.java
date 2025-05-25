package com.wh.reception.domain;


import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "palettes")
public class Palette extends AbstractCargo {

    private static final long serialVersionUID = 1L;

    private Long id;
    private boolean stackable = false;
    private double height; 
    private Dimension dimension;
    private Category category;
    private List<ItemLinePalette> itemLinePalettes;

    public Palette() {
    }

 

    public Palette(boolean stackable, double height, Dimension dimension, Category category,
			List<ItemLinePalette> itemLinePalettes) {
		super();
		this.stackable = stackable;
		this.height = height;
		this.dimension = dimension;
		this.category = category;
		this.itemLinePalettes = itemLinePalettes;
	}



	@Id
    @Column(name = "palette_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(columnDefinition = "boolean default false")
    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    @Column(nullable = false)
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @ManyToOne
    @JoinColumn(name = "dimension_id")
    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    @OneToMany(mappedBy = "palette", fetch = FetchType.LAZY)
    public List<ItemLinePalette> getItemLinePalettes() {
        return itemLinePalettes;
    }

    public void setItemLinePalettes(List<ItemLinePalette> itemLinePalettes) {
        this.itemLinePalettes = itemLinePalettes;
    }

    @Override
    public String toString() {
        return "Palette [id=" + id + ", height=" + height + ", createdAt=" + createdAt + "]";
    }

}