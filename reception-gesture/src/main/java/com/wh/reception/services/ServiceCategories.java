package com.wh.reception.services;

import java.util.List;

import com.wh.reception.domain.Category;

import jakarta.ejb.Remote;

@Remote
public interface ServiceCategories {
	
	/**	
	 * 	CRUD operations for Category.
	 * @param category the category to be managed
	 * @param id the ID of the category
	 */
		// **** Category **** //
	
		// CRUD operations for Category
	
		void addCategory(Category category);
		
		Category updateCategory(Long id,Category category);
		
		void deleteCategory(Long id);
		
		List<Category> findAllCategories();
		
		Category findCategoryById(Long id);
		

}
