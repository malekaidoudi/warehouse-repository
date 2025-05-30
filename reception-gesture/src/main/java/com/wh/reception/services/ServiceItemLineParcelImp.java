package com.wh.reception.services;

import java.util.List;
import java.util.logging.Logger;

import com.wh.reception.domain.Item;
import com.wh.reception.domain.ItemLineParcel;
import com.wh.reception.domain.ItemLineParcelPK;
import com.wh.reception.domain.Parcel;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Stateless
public class ServiceItemLineParcelImp implements ServiceItemLineParcel {

	Logger logger = Logger.getLogger("RECEPTION_SERVICE");

	@PersistenceContext(unitName = "UP_WAREHOUSE")
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public void addItemToParcel(Long itemId, Long parcelId, int quantity) {
		Item item = em.find(Item.class, itemId);
		Parcel parcel = em.find(Parcel.class, parcelId);
		if (item == null || parcel == null) {
			throw new IllegalArgumentException("Item or Parcel not found.");
		}
		ItemLineParcel itemLineParcel = new ItemLineParcel();
		itemLineParcel.setItem(item);
		itemLineParcel.setParcel(parcel);
		itemLineParcel.setQuantity(quantity);
		itemLineParcel.setId(new ItemLineParcelPK(itemId, parcelId));
		em.persist(itemLineParcel);
		logger.info("Item added to parcel successfully");

	}

	@Override
	public void updateItemInParcel(Long itemId, Long parcelId, int quantity) {
		ItemLineParcel itemLineParcel = em.find(ItemLineParcel.class, new ItemLineParcelPK(itemId, parcelId));
		if (itemLineParcel != null) {
			itemLineParcel.setQuantity(quantity);
			em.merge(itemLineParcel);
		} else {
			logger.warning("Item not found in parcel");
		}
		
	}

	@Override
	public void deleteItemFromParcel(Long itemId, Long parcelId) {
		ItemLineParcel itemLineParcel = em.find(ItemLineParcel.class, new ItemLineParcelPK(itemId, parcelId));
		if (itemLineParcel != null) {
			em.remove(itemLineParcel);
		} else {
			logger.warning("Item not found in parcel");
		}
		
	}

	@Override
	public Item findItemInParcel(Long itemId, Long parcelId) {
		ItemLineParcel itemLineParcel = em.find(ItemLineParcel.class, new ItemLineParcelPK(itemId, parcelId));
		if (itemLineParcel != null) {
			return itemLineParcel.getItem();
		} else {
			logger.warning("Item not found in parcel");
		}
		
		return null;
	}

	@Override
	public List<Item> findAllItemsInParcel(Long parcelId) {
		List<ItemLineParcel> itemLineParcels = em.createQuery("SELECT ilp FROM ItemLineParcel ilp WHERE ilp.parcel.id = :parcelId", ItemLineParcel.class)
				.setParameter("parcelId", parcelId)
				.getResultList();
		if (!itemLineParcels.isEmpty()) {
			return itemLineParcels.stream()
					.map(ItemLineParcel::getItem)
					.toList();
		} else {
			logger.warning("No items found in parcel");
		}
		return null;
	}

}
