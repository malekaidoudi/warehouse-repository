package com.wh.reception.services;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.wh.reception.domain.Parcel;
import com.wh.reception.domain.Reception;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
	public List<Parcel> findAllParcel(Long receptionId) {
		if (receptionId == null) {
			throw new IllegalArgumentException("Reception ID cannot be null");
		}
		Reception reception = em.find(Reception.class, receptionId);
		if (reception == null) {
			throw new IllegalArgumentException("Reception with ID " + receptionId + " not found");
		}
		List<Parcel> parcels = reception.getParcels();
		
		if (parcels.isEmpty()) {
			logger.warning("No parcels found for reception with ID " + receptionId);
		} else {
			logger.info("Found " + parcels.size() + " parcels for reception with ID " + receptionId);
		}
		return parcels;
	}
	
	

}
