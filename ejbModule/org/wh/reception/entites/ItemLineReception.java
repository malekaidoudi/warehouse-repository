package org.wh.reception.entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class ItemLineReception implements Serializable{

	private static final long serialVersionUID = 1179042102653887884L;
	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;*/
	@EmbeddedId
	private ItemLineReception_PK ir_PK;
	@Column(nullable = false)
	private int quantite;
	
	@ManyToOne
	@MapsId("item_id")
	@JoinColumn(name = "item_id")
	private Item item;
	
	@ManyToOne
	@MapsId("reception_id")
	@JoinColumn(name = "reception_id")
	private Reception reception;
	
	public ItemLineReception(ItemLineReception_PK ir_PK, int quantite) {
		// dynamic allocation
		this.ir_PK = new ItemLineReception_PK();
		this.quantite = quantite;
	}


	public ItemLineReception() {
		// dynamic allocation
		this.ir_PK = new ItemLineReception_PK();
	}
	
	public ItemLineReception_PK getIr_PK() {
		return ir_PK;
	}


	public void setIr_PK(ItemLineReception_PK ir_PK) {
		this.ir_PK = ir_PK;
	}


	public int getQuantite() {
		return quantite;
	}


	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}


	public Item getItem() {
		return item;
	}


	public void setItem(Item item) {
		this.item = item;
	}


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
