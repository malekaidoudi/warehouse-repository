package com.wh.reception.services;

import java.util.List;

import com.wh.reception.domain.Reception;

import jakarta.ejb.Remote;

@Remote
public interface ServiceReception {
	
	/**
	 * 	CRUD operations for Reception.
	 * @param reception the reception to be managed
	 * @param id the ID of the reception
	 * @param reference the reference of the reception
	 * @param paletteId the ID of the palette
	 * @param parcelId the ID of the parcel
	 * 
	 */
	
	Reception createReception(Reception reception);

	Reception updateReception(Reception reception);

	void deleteReception(Long id);

	Reception findReceptionById(Long id);

	Reception findReceptionByReference(Long reference);

	Reception findReceptionByParcelId(Long parcelId);

	Reception findReceptionByPaletteId(Long paletteId);

	List<Reception> findAllReceptions();
	
	int getPalettesCount(Long receptionId);

	int getParcelsCount(Long receptionId);

	
	


	
	
	
	
	
	
	

}