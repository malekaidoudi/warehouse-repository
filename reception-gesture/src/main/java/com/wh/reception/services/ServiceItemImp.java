package com.wh.reception.services;

import java.util.List;
import java.util.logging.Logger;

import com.wh.reception.domain.Item;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ServiceItemImp implements ServiceItem {
	

	Logger logger = Logger.getLogger("RECEPTION_SERVICE");

	@PersistenceContext(unitName = "UP_WAREHOUSE")
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public void addItem(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}
		item.validate();
		em.persist(item);
		logger.info("Item added successfully");
	}

	@Override
	public void updateItem(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}
		item.validate();
		Item existingItem = em.find(Item.class, item.getId());
		if (existingItem == null) {
			throw new IllegalArgumentException("Item with ID " + item.getId() + " not found.");
		}
		em.merge(item);
		logger.info("Item updated successfully");
		
	}

	@Override
	public void deleteItem(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Item ID cannot be null");
		}
		Item item = em.find(Item.class, id);
		if (item == null) {
			throw new IllegalArgumentException("Item with ID " + id + " not found");
		}
		em.remove(item);
		logger.info("Item deleted successfully");
		
	}

	@Override
	public List<Item> findAllItems() {
		List<Item> items = em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
		if (items.isEmpty()) {
			logger.info("No items found");
		} else {
			logger.info("Items found: " + items.size());
		}
		return items;
		
	}

//	@Override
//	public void addItemToPalette(Long paletteId, Long itemId) {
//		if (paletteId == null || itemId == null) {
//			throw new IllegalArgumentException("Palette ID and Item ID cannot be null");
//		}
//		Item item = em.find(Item.class, itemId);
//		if (item == null) {
//			throw new IllegalArgumentException("Item with ID " + itemId + " not found");
//		}
//		// Logic to add item to palette
//		logger.info("Item added to palette successfully");
//		
//	}

	
	

}
