package com.wh.reception.services;

import java.util.List;
import java.util.logging.Logger;

import com.wh.reception.domain.Item;
import com.wh.reception.domain.ItemLinePalette;
import com.wh.reception.domain.ItemLinePalettePK;
import com.wh.reception.domain.Palette;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Stateless
public class ServiceItemLinePaletteImp implements ServiceItemLinePalette {

	Logger logger = Logger.getLogger("RECEPTION_SERVICE");

	@PersistenceContext(unitName = "UP_WAREHOUSE")
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public void addItemToPalette(Long itemId, Long paletteId, int quantity) {
//		ItemLinePalette itemLinePalette = new ItemLinePalette();
//		itemLinePalette.setId(new ItemLinePalettePK(itemId, paletteId));
//		itemLinePalette.setQuantity(quantity);
//		em.persist(itemLinePalette);
		Item item = em.find(Item.class, itemId);
	    Palette palette = em.find(Palette.class, paletteId);
	    if (item == null || palette == null) {
	        throw new IllegalArgumentException("Item or Palette not found");
	    }
	    ItemLinePalette itemLinePalette = new ItemLinePalette();
	    itemLinePalette.setId(new ItemLinePalettePK(itemId, paletteId));
	    itemLinePalette.setItem(item); // Définit la relation
	    itemLinePalette.setPalette(palette); // Définit la relation
	    itemLinePalette.setQuantity(quantity);
	    em.persist(itemLinePalette);
	    logger.info("Item added to palette successfully");
	}

	@Override
	public void updateItemInPalette(Long itemId, Long paletteId, int quantity) {
		ItemLinePalette itemLinePalette = em.find(ItemLinePalette.class, new ItemLinePalettePK(itemId, paletteId));
		if (itemLinePalette != null) {
			itemLinePalette.setQuantity(quantity);
			em.merge(itemLinePalette);
		} else {
			logger.warning("Item not found in palette");
		}
		
	}

	@Override
	public void deleteItemFromPalette(Long itemId, Long paletteId) {
		ItemLinePalette itemLinePalette = em.find(ItemLinePalette.class, new ItemLinePalettePK(itemId, paletteId));
		if (itemLinePalette != null) {
			em.remove(itemLinePalette);
		} else {
			logger.warning("Item not found in palette");
		}		
	}

	@Override
	public Item findItemInPalette(Long itemId, Long paletteId) {
		ItemLinePalette itemLinePalette = em.find(ItemLinePalette.class, new ItemLinePalettePK(itemId, paletteId));
		if (itemLinePalette != null) {
			return itemLinePalette.getItem();
		} else {
			logger.warning("Item not found in palette");
		}	
		
		
		return null;
	}

	@Override
	public List<Item> findAllItemsInPalette(Long paletteId) {
		List<Item> item = em.createQuery("SELECT ilp.item FROM ItemLinePalette ilp "
				+ "WHERE ilp.palette.id = :paletteId", Item.class)
				.setParameter("paletteId", paletteId)
				.getResultList();
		if (item.isEmpty()) {
			logger.warning("No items found in palette");
		}
		return item;
	}

}
