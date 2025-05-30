package com.wh.reception.services;

import java.util.List;
import java.util.logging.Logger;

import com.wh.reception.domain.Item;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
@Stateless
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
	public Item findItemById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Item ID cannot be null");
		}
		Item item = em.find(Item.class, id);
		if (item == null) {
			logger.info("Item with ID " + id + " not found");
			return null;
		}
		logger.info("Item found: " + item);
		return item;
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
	
	@Override
	public List<Item> findItemsByPaletteId(Long paletteId) {
		if (paletteId == null) {
			throw new IllegalArgumentException("Palette ID cannot be null");
		}
		List<Item> items = em.createQuery(
				"SELECT i FROM Item i JOIN i.itemLinePalettes ilp WHERE ilp.palette.id = :paletteId", Item.class)
				.setParameter("paletteId", paletteId).getResultList();
		if (items.isEmpty()) {
			logger.info("No items found for palette ID " + paletteId);
		} else {
			logger.info("Items found for palette ID " + paletteId + ": " + items.size());
		}
		return items;
		
	}
	
	@Override
	public List<Item> findItemsByParcelId(Long parcelId) {
		if (parcelId == null) {
			throw new IllegalArgumentException("Parcel ID cannot be null");
		}
		List<Item> items = em.createQuery(
				"SELECT i FROM Item i JOIN i.itemLineParcels ilp WHERE ilp.parcel.id = :parcelId", Item.class)
				.setParameter("parcelId", parcelId).getResultList();
		if (items.isEmpty()) {
			logger.info("No items found for parcel ID " + parcelId);
		} else {
			logger.info("Items found for parcel ID " + parcelId + ": " + items.size());
		}
		return items;	
	}
	
	@Override
	public List<Item> findItemsByReceptionId(Long receptionId) {
        if (receptionId == null) {
            throw new IllegalArgumentException("Reception ID cannot be null");
        }

        TypedQuery<Item> query = em.createQuery(
            "SELECT DISTINCT i " +
            "FROM Item i " +
            "WHERE EXISTS (" +
            "    SELECT 1 FROM Reception r " +
            "    JOIN r.parcels p " +
            "    JOIN p.itemLineParcels ilp " +
            "    WHERE ilp.item = i AND r.id = :receptionId" +
            ") OR EXISTS (" +
            "    SELECT 1 FROM Reception r " +
            "    JOIN r.palettes pal " +
            "    JOIN pal.itemLinePalettes ilpal " +
            "    WHERE ilpal.item = i AND r.id = :receptionId" +
            ")",
            Item.class
        );
        query.setParameter("receptionId", receptionId);
        return query.getResultList();
    }
}
	


