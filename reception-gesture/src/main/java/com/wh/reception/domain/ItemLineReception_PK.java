package com.wh.reception.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class ItemLineReception_PK implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private Long receptions_id; 
	private Long item_id;
	public ItemLineReception_PK() {
	}
	public ItemLineReception_PK(Long reception_id, Long item_id) {
		super();
		this.receptions_id = reception_id;
		this.item_id = item_id;
	}
	public Long getReception_id() {
		return receptions_id;
	}
	public void setReception_id(Long reception_id) {
		this.receptions_id = reception_id;
	}
	public Long getItem_id() {
		return item_id;
	}
	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}
	@Override
	public int hashCode() {
		return Objects.hash(item_id, receptions_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemLineReception_PK other = (ItemLineReception_PK) obj;
		return Objects.equals(item_id, other.item_id) && Objects.equals(receptions_id, other.receptions_id);
	}
}
