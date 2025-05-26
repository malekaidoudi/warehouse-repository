package com.wh.reception.services;

import java.util.List;
import java.util.logging.Logger;

import com.wh.reception.domain.Dimension;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Stateless
public class ServiceDimensionImp implements ServiceDimension {

	Logger logger = Logger.getLogger("RECEPTION_SERVICE");

	@PersistenceContext(unitName = "UP_WAREHOUSE")
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public void addDimension(Dimension dimension) {
		if (dimension == null) {
			throw new IllegalArgumentException("Dimension cannot be null");
		}
		dimension.validate();
		em.persist(dimension);
		logger.info("Successfully added dimension with id: " + dimension.getId());

	}

	@Override
	public void updateDimension(Dimension dimension) {
		if (dimension == null || dimension.getId() == null) {
			throw new IllegalArgumentException("Dimension or its ID cannot be null");
		}
		dimension.validate();
		Dimension existing = em.find(Dimension.class, dimension.getId());
		if (existing == null) {
			throw new IllegalArgumentException("Dimension with ID " + dimension.getId() + " not found");
		}
		em.merge(dimension);

	}

	@Override
	public void deleteDimension(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Dimension ID cannot be null");
		}
		Dimension dimension = em.find(Dimension.class, id);
		if (dimension == null) {
			throw new IllegalArgumentException("Dimension with ID " + id + " not found");
		}
		em.remove(dimension);
		logger.info("Successfully deleted dimension with id: " + id);

	}

	@Override
	public List<Dimension> findAllDimensions() {
		List<Dimension> dimensions = 
				em.createQuery("SELECT d FROM Dimension d", Dimension.class).getResultList();
		
		if (dimensions.isEmpty()) {
			logger.warning("No dimensions found");
		} else {
			logger.info("Found " + dimensions.size() + " dimensions");
		}
		return dimensions;
	}

}
