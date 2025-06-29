package com.wh.reception.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.wh.reception.domain.Dimension;
import com.wh.reception.exception.InvalidDataException;
import com.wh.reception.exception.NotFoundException;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
@Stateless
public class ServiceDimensionImp implements ServiceDimension {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDimensionImp.class);

	@PersistenceContext(unitName = "UP_WAREHOUSE")
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public Dimension addDimension(Dimension dimension) {
		if (dimension == null) {
			throw new IllegalArgumentException("Dimension cannot be null");
		}
		if (dimension.getLabel() == null || dimension.getLabel().trim().isEmpty()) {
			LOGGER.error("The dimension label cannot be null or empty.");
			throw new InvalidDataException("The dimension label cannot be null or empty.");
		}
		if (dimension.getLabel().length() < 3 || dimension.getLabel().length() > 50) {
			LOGGER.error("The label must be between 3 and 50 characters.");
			throw new InvalidDataException("The label must be between 3 and 50 characters.");
		}
		if (dimension.getLength() < dimension.getWidth() || dimension.getWidth() <= 0) {
			LOGGER.error("Dimension length must be positive.");
			throw new InvalidDataException("The dimension width must be positive and smaller than the length.");
		}
		try {
			TypedQuery<Long> query = em
					.createQuery("SELECT COUNT(d) FROM Dimension d WHERE LOWER(d.label) = LOWER(:label)", Long.class);
			query.setParameter("label", dimension.getLabel());
			if (query.getSingleResult() > 0) {
				LOGGER.error("A dimension label '{}' already exists.", dimension.getLabel());
				throw new InvalidDataException("A dimension label " + dimension.getLabel() + " already exists.");
			}
		} catch (InvalidDataException e) {
			LOGGER.error("Error checking for duplicate dimension label: {}", dimension.getLabel(), e);
			throw new InvalidDataException("A dimension label " + dimension.getLabel() + " already exists.");
		} catch (Exception err) {
			// Log the error and throw a runtime exception
			// This will be caught by the mapper and converted to a 500 error
			LOGGER.error("Unexpected error while checking category label uniqueness: {}", dimension.getLabel(), err);
			throw new RuntimeException("Unexpected error while checking category label uniqueness.", err);
		}
		em.persist(dimension);
		LOGGER.info("Successfully added dimension with id: " + dimension.getId());
		return dimension;

	}

	@Override
	public Dimension updateDimension(Long id ,Dimension dimension) {
		if (dimension == null || dimension.getId() == null) {
			throw new IllegalArgumentException("Dimension or its ID cannot be null");
		}
		Dimension existingDimension = em.find(Dimension.class, id);
		
		if (existingDimension == null) {
			throw new NotFoundException("Dimension with ID " + dimension.getId() + " not found");
		}
		if (dimension.getLabel() != null && (dimension.getLabel().length() < 3 || dimension.getLabel().length() > 50)) {
			throw new InvalidDataException("The dimension label must be between 3 and 50 characters.");
		}
		if (dimension.getLabel() == null || dimension.getLabel().trim().isEmpty()) {
			dimension.setLabel(existingDimension.getLabel());
		}
		if((dimension.getLength()==null) && (dimension.getWidth()== null)) {
			dimension.setLength(existingDimension.getLength());
			dimension.setWidth(existingDimension.getWidth());
		}
		if (dimension.getLength() < dimension.getWidth() || dimension.getWidth() <= 1) {
			LOGGER.error("Dimension width must be positive and smaller than the length.");
			throw new InvalidDataException("the dimension width must be positive and smaller than the length.");
		}
		try {
			TypedQuery<Long> query = em.createQuery(
					"SELECT COUNT(d) FROM Dimension d WHERE LOWER(d.label) = LOWER(:label) AND d.id != :id",
					Long.class);
			query.setParameter("label", dimension.getLabel());
			query.setParameter("id", dimension.getId());
			if (query.getSingleResult() > 0) {
				LOGGER.error("A dimension label '{}' already exists.", dimension.getLabel());
				throw new InvalidDataException("A dimension label " + dimension.getLabel() + " already exists.");
			}
		} catch (InvalidDataException e) {
			LOGGER.error("Error checking for duplicate dimension label: {}", dimension.getLabel(), e);
			throw new InvalidDataException("A dimension label " + dimension.getLabel() + " already exists.");
		} catch (Exception err) {
			LOGGER.error("Unexpected error while checking dimension label uniqueness: {}", dimension.getLabel(), err);
			throw new RuntimeException("Unexpected error while checking dimension label uniqueness.", err);
		}
			LOGGER.info("Successfully updated dimension: {}", dimension);
			return em.merge(dimension);
	}

	@Override
	public void deleteDimension(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Dimension ID cannot be null");
		}
		Dimension dimension = em.find(Dimension.class, id);
		if (dimension == null) {
			throw new NotFoundException("Dimension with ID " + id + " not found");
		}
		em.remove(dimension);
		LOGGER.info("Successfully deleted dimension with id: " + id);

	}

	@Override
	public List<Dimension> findAllDimensions() {
		List<Dimension> dimensions = 
				em.createQuery("SELECT d FROM Dimension d", Dimension.class).getResultList();
		
		if (dimensions.isEmpty()) {
			LOGGER.warn("No dimensions found");
		} else {
			LOGGER.info("Found " + dimensions.size() + " dimensions");
		}
		return dimensions;
	}
	
	@Override
	public Dimension findDimensionById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Dimension ID cannot be null");
		}
		Dimension dimension = em.find(Dimension.class, id);
		if (dimension == null) {
			LOGGER.warn("Dimension with ID " + id + " not found");
			throw new NotFoundException("Dimension with ID " + id + " not found");
		}
		LOGGER.info("Found dimension with id: " + id);
		return dimension;
	}

}
