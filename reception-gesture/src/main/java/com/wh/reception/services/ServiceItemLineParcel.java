package com.wh.reception.services;

import java.util.List;

import com.wh.reception.domain.Item;

import jakarta.ejb.Remote;

@Remote
public interface ServiceItemLineParcel {
	
	/**
	 *  CRUD ItemLineParcel.
	 * 
	 * @param itemId   the ID of the item
	 * @param parcelId the ID of the parcel
	 * @param quantity  the quantity of the item 
	 */
	
	
	void addItemToParcel(Long itemId, Long parcelId, int quantity);
	
	void updateItemInParcel(Long itemId, Long parcelId, int quantity);
	
	void deleteItemFromParcel(Long itemId, Long parcelId);
	
	Item findItemInParcel(Long itemId, Long parcelId);
	
	List<Item> findAllItemsInParcel(Long parcelId);

}
