package com.wh.reception.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name="ItemLineReception")
public class ItemLineReception implements Serializable{
	
	
	
	private static final long serialVersionUID = 1L;
	
	
	private ItemLineReception_PK ir_PK;
	private int quantite;
	private Item item;
	private Reception reception;
	
	
	public ItemLineReception(ItemLineReception_PK ir_PK, int quantite) {
		this.ir_PK = ir_PK;
		this.quantite = quantite;
	}
	public ItemLineReception(int quantite) {
		// dynamic allocation
		this.ir_PK = new ItemLineReception_PK();
		this.quantite = quantite;
	}
	public ItemLineReception() {
		// dynamic allocation
		this.ir_PK = new ItemLineReception_PK();
	}
	
	@EmbeddedId
	public ItemLineReception_PK getIr_PK() {
		return ir_PK;
	}


	public void setIr_PK(ItemLineReception_PK ir_PK) {
		this.ir_PK = ir_PK;
	}

	@Column(nullable = false)
	public int getQuantite() {
		return quantite;
	}


	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	@ManyToOne
	@MapsId("item_id")
	@JoinColumn(name = "item_id")
	public Item getItem() {
		return item;
	}


	public void setItem(Item item) {
		this.item = item;
	}

	@ManyToOne
	@MapsId("receptions_id")
	@JoinColumn(name = "reception_id")
	public Reception getReception() {
		return reception;
	}


	public void setReception(Reception reception) {
		this.reception = reception;
	}


	@Override
	public String toString() {
		return "ItemLineReception [item=" + item + ", reception=" + reception + "]";
	}


}
