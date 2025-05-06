package com.wh.reception.domain;

import java.io.Serializable;
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
@Table(name="items")
public class Item implements Serializable{


	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String label;
	private String description;

	private List<ItemLineReception> ItemLineReception;
	
	@OneToMany(mappedBy = "item",fetch = FetchType.LAZY)
	public List<ItemLineReception> getItemLineReception() {
		return ItemLineReception;
	}

	public void setItemLineReception(List<ItemLineReception> itemLineReception) {
		ItemLineReception = itemLineReception;
	}

	public Item() {
	}

	public Item(String label, String description) {
		this.label = label;
		this.description = description;
	}
	
	@Id @Column(name="item_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="label_item",nullable = false,length = 50)
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name="description_item")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", label=" + label + "]";
	}
	
	
}
