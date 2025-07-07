package com.wh.reception.mapper;

import com.wh.reception.dto.ItemDTO;
import com.wh.reception.dto.update.ItemUpdateDTO;

/**
 * Mapper interface for Item entity and DTOs
 * This interface defines the contract for mapping between domain objects and DTOs
 * Implementation will be provided in the EJB module
 */
public interface ItemMapper {

    /**
     * Convert Item entity to ItemDTO
     * @param item the Item entity
     * @return ItemDTO
     */
    ItemDTO toDTO(Object item);

    /**
     * Convert ItemDTO to Item entity
     * @param itemDTO the ItemDTO
     * @return Item entity
     */
    Object toEntity(ItemDTO itemDTO);

    /**
     * Update Item entity with values from ItemUpdateDTO
     * @param item the Item entity to update
     * @param updateDTO the ItemUpdateDTO with new values
     * @return updated Item entity
     */
    Object updateEntity(Object item, ItemUpdateDTO updateDTO);

    /**
     * Create a new Item entity from ItemDTO
     * @param itemDTO the ItemDTO
     * @return new Item entity
     */
    Object createEntity(ItemDTO itemDTO);
}