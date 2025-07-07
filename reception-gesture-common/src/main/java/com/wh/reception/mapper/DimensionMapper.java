package com.wh.reception.mapper;

import com.wh.reception.dto.DimensionDTO;
import com.wh.reception.dto.update.DimensionUpdateDTO;

/**
 * Mapper interface for Dimension entity and DTOs
 * This interface defines the contract for mapping between domain objects and DTOs
 * Implementation will be provided in the EJB module
 */
public interface DimensionMapper {

    /**
     * Convert Dimension entity to DimensionDTO
     * @param dimension the Dimension entity
     * @return DimensionDTO
     */
    DimensionDTO toDTO(Object dimension);

    /**
     * Convert DimensionDTO to Dimension entity
     * @param dimensionDTO the DimensionDTO
     * @return Dimension entity
     */
    Object toEntity(DimensionDTO dimensionDTO);

    /**
     * Update Dimension entity with values from DimensionUpdateDTO
     * @param dimension the Dimension entity to update
     * @param updateDTO the DimensionUpdateDTO with new values
     * @return updated Dimension entity
     */
    Object updateEntity(Object dimension, DimensionUpdateDTO updateDTO);

    /**
     * Create a new Dimension entity from DimensionDTO
     * @param dimensionDTO the DimensionDTO
     * @return new Dimension entity
     */
    Object createEntity(DimensionDTO dimensionDTO);
}