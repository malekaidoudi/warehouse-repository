package org.wh.reception.entites;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="items")
public class Item implements Serializable{
	
	private static final long serialVersionUID = 5110044344688892028L;
	
	@Id
	@Column(name="item_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="label_item",nullable = false,length = 50)
	private String label;
	@Column(name="description_item")
	private String description;
	
	@OneToMany(mappedBy = "item",fetch = FetchType.LAZY)
	private List<ItemLineReception> ItemLineReception;
	
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

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
