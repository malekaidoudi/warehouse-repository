package com.wh.reception.mapper;

import java.util.List;

import com.wh.reception.dto.ItemLineParcelDTO;
import com.wh.reception.dto.update.ItemLineParcelUpdateDTO;

/**
 * Mapper interface for ItemLineParcel entity and DTOs
 * This interface defines the contract for mapping between domain objects and DTOs
 * Implementation will be provided in the EJB module
 */
public interface ItemLineParcelMapper {

    /**
     * Convert ItemLineParcel entity to ItemLineParcelDTO
     * @param itemLineParcel the ItemLineParcel entity
     * @return ItemLineParcelDTO
     */
    ItemLineParcelDTO toDTO(Object itemLineParcel);

    /**
     * Convert ItemLineParcelDTO to ItemLineParcel entity
     * @param itemLineParcelDTO the ItemLineParcelDTO
     * @return ItemLineParcel entity
     */
    Object toEntity(ItemLineParcelDTO itemLineParcelDTO);

    /**
     * Update ItemLineParcel entity with values from ItemLineParcelUpdateDTO
     * @param itemLineParcel the ItemLineParcel entity to update
     * @param updateDTO the ItemLineParcelUpdateDTO with new values
     * @return updated ItemLineParcel entity
     */
    Object updateEntity(Object itemLineParcel, ItemLineParcelUpdateDTO updateDTO);

    /**
     * Create a new ItemLineParcel entity from ItemLineParcelDTO
     * @param itemLineParcelDTO the ItemLineParcelDTO
     * @return new ItemLineParcel entity
     */
    Object createEntity(ItemLineParcelDTO itemLineParcelDTO);

    /**
     * Convert list of ItemLineParcel entities to list of ItemLineParcelDTOs
     * @param itemLineParcels the list of ItemLineParcel entities
     * @return list of ItemLineParcelDTOs
     */
    List<ItemLineParcelDTO> toDTOList(List<Object> itemLineParcels);

    /**
     * Convert list of ItemLineParcelDTOs to list of ItemLineParcel entities
     * @param itemLineParcelDTOs the list of ItemLineParcelDTOs
     * @return list of ItemLineParcel entities
     */
    List<Object> toEntityList(List<ItemLineParcelDTO> itemLineParcelDTOs);
}