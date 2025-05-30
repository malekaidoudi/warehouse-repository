package com.wh.reception.services;

import java.util.List;

import com.wh.reception.domain.Item;

import jakarta.ejb.Remote;

@Remote
public interface ServiceItemLinePalette {
	
	/**
	 *  CRUD ItemLinePalette.
	 * 
	 * @param itemId   the ID of the item 
	 * @param paletteId the ID of the palette 
	 * @param quantity  the quantity of the item 
	 * 
	 */
	
	
	void addItemToPalette(Long itemId, Long paletteId, int quantity);
	
	void updateItemInPalette(Long itemId, Long paletteId, int quantity);
	
	void deleteItemFromPalette(Long itemId, Long paletteId);
	
	Item findItemInPalette(Long itemId, Long paletteId);
	
	List<Item> findAllItemsInPalette(Long paletteId);

}
