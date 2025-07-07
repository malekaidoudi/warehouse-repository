package com.wh.reception.mapper;

import com.wh.reception.dto.ParcelDTO;
import com.wh.reception.dto.update.ParcelUpdateDTO;

/**
 * Mapper interface for Parcel entity and DTOs
 * This interface defines the contract for mapping between domain objects and DTOs
 * Implementation will be provided in the EJB module
 */
public interface ParcelMapper {

    /**
     * Convert Parcel entity to ParcelDTO
     * @param parcel the Parcel entity
     * @return ParcelDTO
     */
    ParcelDTO toDTO(Object parcel);

    /**
     * Convert ParcelDTO to Parcel entity
     * @param parcelDTO the ParcelDTO
     * @return Parcel entity
     */
    Object toEntity(ParcelDTO parcelDTO);

    /**
     * Update Parcel entity with values from ParcelUpdateDTO
     * @param parcel the Parcel entity to update
     * @param updateDTO the ParcelUpdateDTO with new values
     * @return updated Parcel entity
     */
    Object updateEntity(Object parcel, ParcelUpdateDTO updateDTO);

    /**
     * Create a new Parcel entity from ParcelDTO
     * @param parcelDTO the ParcelDTO
     * @return new Parcel entity
     */
    Object createEntity(ParcelDTO parcelDTO);
}