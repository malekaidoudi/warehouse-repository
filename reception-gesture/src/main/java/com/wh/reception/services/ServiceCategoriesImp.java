package com.wh.reception.services;

import java.util.List;
import java.util.logging.Logger;

import com.wh.reception.domain.Category;
import com.wh.reception.exception.NotFoundException;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class ServiceCategoriesImp implements ServiceCategories {
	
	Logger logger = Logger.getLogger("RECEPTION_SERVICE");

	@PersistenceContext(unitName = "UP_WAREHOUSE")
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	@Override
	public void addCategory(Category category) {
		if (category == null) {
			logger.severe("Category cannot be null");
			throw new IllegalArgumentException("Category cannot be null");
		}
		em.persist(category);
		
		logger.info("Successfully added category with label: " + category.getLabel());
	}

	@Override
	public void updateCategory(Category category) {
		if (category == null || category.getId() == null) {
			logger.severe("Category or its ID cannot be null");
			throw new IllegalArgumentException("Category or its ID cannot be null");
		}
		Category existing = em.find(Category.class, category.getId());
		if (existing == null) {
			logger.warning("Category with ID " + category.getId() + " not found");
			throw new NotFoundException("Category with ID " + category.getId() + " not found");
		}
		em.merge(category);
		
	}

	@Override
	public void deleteCategory(Long id) {
		if (id == null) {
			 logger.severe("Category ID cannot be null");
			throw new IllegalArgumentException("Category ID cannot be null");
		}
		Category category = em.find(Category.class, id);
		if (category == null) {
			logger.warning("Category with ID " + id + " not found");
			throw new NotFoundException("Category with ID " + id + " not found");
		}
		em.remove(category);
		logger.info("Successfully deleted category with id: " + id);
	}

	@Override
	public List<Category> findAllCategories() {
		
		TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
		
		List<Category> categories = query.getResultList();
		
		if (categories.isEmpty()) {
			logger.warning("No categories found");
		} else {
			logger.info("Found " + categories.size() + " categories");
		}
		
		return categories;
	}
	
	@Override
	public Category findCategoryById(Long id) {
		if (id == null) {
			logger.severe("Category ID cannot be null");
			throw new IllegalArgumentException("Category ID cannot be null");
		}
		Category category = em.find(Category.class, id);
		if (category == null) {
			logger.warning("Category with ID " + id + " not found");
			throw new NotFoundException("Category with ID " + id + " not found");
		} else {
			logger.info("Found category with id: " + id);
			return category;
		}
	}

}
