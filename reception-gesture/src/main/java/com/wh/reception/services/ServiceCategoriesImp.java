package com.wh.reception.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wh.reception.domain.Category;
import com.wh.reception.exception.InvalidDataException;
import com.wh.reception.exception.NotFoundException;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class ServiceCategoriesImp implements ServiceCategories {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceCategoriesImp.class);

	@PersistenceContext(unitName = "UP_WAREHOUSE")
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public void addCategory(Category category) {
		if (category == null) {
			LOGGER.error("Category cannot be null during add operation.");
			// Utilisez un message brut pour le moment, le mapper s'occupera du code
			// d'erreur
			throw new InvalidDataException("Category cannot be null.");
		}
		if (category.getLabel() == null || category.getLabel().trim().isEmpty()) {
			LOGGER.error("Label of the category cannot be null or empty.");
			throw new InvalidDataException("Label of the category cannot be null or empty.");
		}
		if (category.getLabel().length() < 3 || category.getLabel().length() > 50) {
			LOGGER.error("Category label must be between 3 and 50 characters.");
			throw new InvalidDataException("Category label must be between 3 and 50 characters.");
		}

		try {
			TypedQuery<Long> query = em
					.createQuery("SELECT COUNT(c) FROM Category c WHERE LOWER(c.label) = LOWER(:label)", Long.class);
			query.setParameter("label", category.getLabel());
			if (query.getSingleResult() > 0) {
				LOGGER.error("A category with the label '{}' already exists.", category.getLabel());
				throw new InvalidDataException("A category with the label " + category.getLabel() + " already exists.");
			}
		} catch (InvalidDataException e) {
			LOGGER.error("Error checking for duplicate category label: {}", category.getLabel(), e);
			throw new InvalidDataException("A category with the label " + category.getLabel() + " already exists.");
		} catch (Exception err) {
			// Log the error and throw a runtime exception
			// This will be caught by the mapper and converted to a 500 error
			LOGGER.error("Unexpected error while checking category label uniqueness: {}", category.getLabel(), err);
			throw new RuntimeException("Unexpected error while checking category label uniqueness.", err);
		}

		em.persist(category);
		LOGGER.info("Successfully added category with label: {}", category.getLabel());

	}

	@Override
	public Category updateCategory(Long id, Category category) {
		if (category == null || category.getId() == null) {
			LOGGER.error("Category or its ID cannot be null during update operation.");
			throw new InvalidDataException("Category or its ID cannot be null during update operation.");
		}
		Category existingCategory = em.find(Category.class, id);
		if (existingCategory == null) {
			LOGGER.warn("Category with ID {} not found for update.", id);
			throw new NotFoundException("Category with ID " + id + " not found for update.");
		}
			if (category.getLabel() == null || category.getLabel().trim().isEmpty()) {
				category.setLabel(existingCategory.getLabel());
			} 
			else if (category.getLabel() != null && !category.getLabel().trim().isEmpty()) {
			//else {
			if (category.getLabel().length() < 3 || category.getLabel().length() > 50) {
				LOGGER.error("The label of the category must be between 3 and 50 characters for update.");
				throw new InvalidDataException(
						"The label of the category must be between 3 and 50 characters for update.");
			}
			try {
				TypedQuery<Long> query = em.createQuery(
						"SELECT COUNT(c) FROM Category c WHERE LOWER(c.label) = LOWER(:label) AND c.id != :id",
						Long.class);
				query.setParameter("label", category.getLabel());
				query.setParameter("id", id);
				System.out.println("Query: " + query.getSingleResult());
				if (query.getSingleResult() > 0) {
					LOGGER.error("Another category with the label '{}' already exists during update for ID {}.",
							category.getLabel(), id);
					throw new InvalidDataException("Another category with the label '" + category.getLabel()
							+ "' already exists during update for ID " + id + ".");
				}
			}catch (InvalidDataException e) {
				LOGGER.error("Error checking for duplicate category label: {}", category.getLabel(), e);
				throw new InvalidDataException("Another category with the label " + category.getLabel() + " already exists.");
			} catch (Exception err) {
				LOGGER.error("Error checking for duplicate category label during update for ID {}: {}", id,
						category.getLabel(), err);
				throw new RuntimeException(
						"Internal error while checking category label uniqueness during update for ID " + id, err);
			}
			existingCategory.setLabel(category.getLabel());
		}
		
		// Update description if provided
		if (category.getDescription() == null) {
			category.setDescription(existingCategory.getDescription());
		}
		// Merge the updated category
		try {
			LOGGER.info("Successfully updated category with ID: {}", category);
			return em.merge(category);
		} catch (Exception e) {
			LOGGER.error("Error updating category description for ID {}: {}", id, e.getMessage(), e);
			throw new RuntimeException("Error updating category description for ID " + id, e);
		}
		
	}

	@Override
	public void deleteCategory(Long id) {
		if (id == null) {
			LOGGER.error("Category ID cannot be null during delete operation.");
			throw new InvalidDataException("Category ID cannot be null during delete operation.");
		}
		if (id <= 0) {
			LOGGER.error("Invalid category ID provided: {}. It must be a positive number.", id);
			throw new InvalidDataException("Invalid category ID provided: " + id + ". It must be a positive number.");
		}

		Category category = em.find(Category.class, id);
		if (category == null) {
			LOGGER.warn("Category with ID {} not found for deletion.", id);
			throw new NotFoundException("Category with ID " + id + " not found for deletion.");
		}
		em.remove(category);
		LOGGER.info("Successfully deleted category with id: {}", id);
	}

	@Override
	public List<Category> findAllCategories() {
		TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
		List<Category> categories = query.getResultList();
		if (categories.isEmpty()) {
			LOGGER.info("No categories found.");
		} else {
			LOGGER.info("Found {} categories.", categories.size());
		}
		return categories;

	}

	@Override
	public Category findCategoryById(Long id) {
		if (id == null) {
			LOGGER.error("Category ID cannot be null during find operation.");
			throw new InvalidDataException("Category ID cannot be null during find operation.");
		}
		if (id <= 0) {
			throw new InvalidDataException("Invalid category ID provided: " + id + ". It must be a positive number.");
		}

		Category category = em.find(Category.class, id);
		if (category == null) {
			LOGGER.warn("Category with ID {} not found.", id);
			throw new NotFoundException("Category with ID " + id + " not found.");
		} else {
			LOGGER.info("Found category with id: {}", id);
			return category;
		}
	}

}
