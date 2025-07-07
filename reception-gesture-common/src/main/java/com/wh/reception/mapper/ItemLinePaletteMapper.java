package com.wh.reception.mapper;

import java.util.List;

import com.wh.reception.dto.ItemLinePaletteDTO;
import com.wh.reception.dto.update.ItemLinePaletteUpdateDTO;

/**
 * Mapper interface for ItemLinePalette entity and DTOs
 * This interface defines the contract for mapping between domain objects and DTOs
 * Implementation will be provided in the EJB module
 */
public interface ItemLinePaletteMapper {

    /**
     * Convert ItemLinePalette entity to ItemLinePaletteDTO
     * @param itemLinePalette the ItemLinePalette entity
     * @return ItemLinePaletteDTO
     */
    ItemLinePaletteDTO toDTO(Object itemLinePalette);

    /**
     * Convert ItemLinePaletteDTO to ItemLinePalette entity
     * @param itemLinePaletteDTO the ItemLinePaletteDTO
     * @return ItemLinePalette entity
     */
    Object toEntity(ItemLinePaletteDTO itemLinePaletteDTO);

    /**
     * Update ItemLinePalette entity with values from ItemLinePaletteUpdateDTO
     * @param itemLinePalette the ItemLinePalette entity to update
     * @param updateDTO the ItemLinePaletteUpdateDTO with new values
     * @return updated ItemLinePalette entity
     */
    Object updateEntity(Object itemLinePalette, ItemLinePaletteUpdateDTO updateDTO);

    /**
     * Create a new ItemLinePalette entity from ItemLinePaletteDTO
     * @param itemLinePaletteDTO the ItemLinePaletteDTO
     * @return new ItemLinePalette entity
     */
    Object createEntity(ItemLinePaletteDTO itemLinePaletteDTO);

    /**
     * Convert list of ItemLinePalette entities to list of ItemLinePaletteDTOs
     * @param itemLinePalettes the list of ItemLinePalette entities
     * @return list of ItemLinePaletteDTOs
     */
    List<ItemLinePaletteDTO> toDTOList(List<Object> itemLinePalettes);

    /**
     * Convert list of ItemLinePaletteDTOs to list of ItemLinePalette entities
     * @param itemLinePaletteDTOs the list of ItemLinePaletteDTOs
     * @return list of ItemLinePalette entities
     */
    List<Object> toEntityList(List<ItemLinePaletteDTO> itemLinePaletteDTOs);
}