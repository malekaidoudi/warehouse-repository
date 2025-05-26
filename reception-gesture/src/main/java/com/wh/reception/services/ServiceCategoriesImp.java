package com.wh.reception.services;

import java.util.List;
import java.util.logging.Logger;

import com.wh.reception.domain.Category;

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
			throw new IllegalArgumentException("Category cannot be null");
		}
		category.validate();
		em.persist(category);
		logger.info("Successfully added category with id: " + category.getId());
	}

	@Override
	public void updateCategory(Category category) {
		if (category == null || category.getId() == null) {
			throw new IllegalArgumentException("Category or its ID cannot be null");
		}
		category.validate();
		Category existing = em.find(Category.class, category.getId());
		if (existing == null) {
			throw new IllegalArgumentException("Category with ID " + category.getId() + " not found");
		}
		em.merge(category);
		
	}

	@Override
	public void deleteCategory(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Category ID cannot be null");
		}
		Category category = em.find(Category.class, id);
		if (category == null) {
			throw new IllegalArgumentException("Category with ID " + id + " not found");
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
			return null;
		} else {
			logger.info("Found " + categories.size() + " categories");
		}
	
		return categories;
	}

}
