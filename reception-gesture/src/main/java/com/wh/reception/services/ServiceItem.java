package com.wh.reception.services;

import java.util.List;

import com.wh.reception.domain.Item;

import jakarta.ejb.Remote;

@Remote
public interface ServiceItem {
	
	/**
	 * 	CRUD operations for Item.
	 * @param item the Item to be managed
	 * @param id the ID of the item
	 * @param paletteId the ID of the palette
	 * @param parcelId the ID of the parcel
	 * @param receptionId the ID of the reception
	 * 
	 */

		void addItem(Item item);
		
		void updateItem(Item item);
		
		void deleteItem(Long id);
		
		Item findItemById(Long id);

		List<Item> findAllItems();
		
		List<Item> findItemsByPaletteId(Long paletteId);
		
		List<Item> findItemsByParcelId(Long parcelId);
		
		List<Item> findItemsByReceptionId(Long receptionId);

}
