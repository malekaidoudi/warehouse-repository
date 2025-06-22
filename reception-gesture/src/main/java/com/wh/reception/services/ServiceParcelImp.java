package com.wh.reception.services;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.wh.reception.domain.Parcel;
import com.wh.reception.domain.Reception;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class ServiceParcelImp implements ServiceParcel {

	// parcel without items

	Logger logger = Logger.getLogger("RECEPTION_SERVICE");

	@PersistenceContext(unitName = "UP_WAREHOUSE")
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public void addParcel(Parcel parcel) {
		if (parcel.getReception() == null) {
			throw new IllegalArgumentException("A parcel must be associated with a valid reception.");
		}
		Reception reception = em.find(Reception.class, parcel.getReception().getId());
		if (reception == null) {
			throw new IllegalArgumentException("The specified reception does not exist.");
		}
		parcel.validate();
		reception.getParcels().add(parcel);
		em.persist(parcel);
		logger.info("Successfully added parcel with id: " + parcel.getId());
	}

	@Override
	public void updateParcel(Parcel parcel) {
		if (parcel == null || parcel.getId() == null) {
			throw new IllegalArgumentException("Parcel or its ID cannot be null");
		}
		Parcel existing = em.find(Parcel.class, parcel.getId());
		if (existing == null) {
			throw new IllegalArgumentException("Parcel with ID " + parcel.getId() + " not found");
		}
		Reception reception = em.find(Reception.class, parcel.getReception().getId());
		if (reception == null) {
			throw new IllegalArgumentException("The specified reception does not exist.");
		}
		parcel.validate();
		parcel.setUpdatedAt(new Date());
		em.merge(parcel);
		logger.info("Successfully updated parcel with id: " + parcel.getId());
	}

	@Override
	public void deleteParcel(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Parcel ID cannot be null");
		}
		Parcel parcel = em.find(Parcel.class, id);
		if (parcel == null) {
			throw new IllegalArgumentException("Parcel with ID " + id + " not found");
		}
		Reception reception = parcel.getReception();
		if (reception != null) {
			reception.getParcels().remove(parcel);
		}

		em.remove(parcel);
		logger.info("Successfully deleted parcel with id: " + id);
	}

	@Override
	public Parcel findParcelById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Parcel ID cannot be null");
		}
		Parcel parcel = em.find(Parcel.class, id);
		if (parcel == null) {
			throw new IllegalArgumentException("Parcel with ID " + id + " not found");
		}
		return parcel;
	}

	@Override
	public List<Parcel> findAllParcel() {
		TypedQuery<Parcel> query = em.createQuery("SELECT p FROM Parcel p", Parcel.class);
		List<Parcel> parcels = query.getResultList();
		if (parcels.isEmpty()) {
			logger.info("No parcels found in the database.");
		} else {
			logger.info("Found " + parcels.size() + " parcels in the database.");
		}
		return parcels;
		}	
	@Override
	public List<Parcel> findParcelsByReceptionId(Long receptionId) {
		if (receptionId == null) {
			throw new IllegalArgumentException("Reception ID cannot be null");
		}
		TypedQuery <Parcel> query = em.createQuery(
				"SELECT p FROM Parcel p WHERE p.reception.id = :receptionId", Parcel.class);
		query.setParameter("receptionId", receptionId);
		List<Parcel> parcels = query.getResultList();
		
		if (parcels.isEmpty()) {
			logger.info("No parcels found for reception ID " + receptionId);
		} else {
			logger.info("Parcels found for reception ID " + receptionId + ": " + parcels.size());
		}
		return parcels;
	}

	@Override
	public List<Parcel> findParcelsByItemId(Long itemId) {
		if (itemId == null) {
			throw new IllegalArgumentException("Item ID cannot be null");
		}
		TypedQuery<Parcel> query = em.createQuery(
				"SELECT p FROM Parcel p JOIN p.items i WHERE i.id = :itemId", Parcel.class);
		query.setParameter("itemId", itemId);
		List<Parcel> parcels = query.getResultList();
		
		if (parcels.isEmpty()) {
			logger.info("No parcels found for item ID " + itemId);
		} else {
			logger.info("Parcels found for item ID " + itemId + ": " + parcels.size());
		}
		return parcels;
	}
}
