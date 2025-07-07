package com.wh.reception.mapper;

import com.wh.reception.dto.CategoryDTO;
import com.wh.reception.dto.update.CategoryUpdateDTO;

/**
 * Mapper interface for Category entity and DTOs
 * This interface defines the contract for mapping between domain objects and DTOs
 * Implementation will be provided in the EJB module
 */
public interface CategoryMapper {

    /**
     * Convert Category entity to CategoryDTO
     * @param category the Category entity
     * @return CategoryDTO
     */
    CategoryDTO toDTO(Object category);

    /**
     * Convert CategoryDTO to Category entity
     * @param categoryDTO the CategoryDTO
     * @return Category entity
     */
    Object toEntity(CategoryDTO categoryDTO);

    /**
     * Update Category entity with values from CategoryUpdateDTO
     * @param category the Category entity to update
     * @param updateDTO the CategoryUpdateDTO with new values
     * @return updated Category entity
     */
    Object updateEntity(Object category, CategoryUpdateDTO updateDTO);

    /**
     * Create a new Category entity from CategoryDTO
     * @param categoryDTO the CategoryDTO
     * @return new Category entity
     */
    Object createEntity(CategoryDTO categoryDTO);
}