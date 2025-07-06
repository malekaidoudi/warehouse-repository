package com.wh.reception.services;

import java.util.List;

import com.wh.reception.domain.Parcel;

import jakarta.ejb.Remote;

@Remote
public interface ServiceParcel {

	/**
	 * CRUD operations for Parcel.
	 * @param parcel the parcel to be managed
	 * @param id the ID of the parcel
	 * @param receptionId the ID of the reception
	 * 
	 */

		void addParcel(Parcel parcel);

		Parcel updateParcel(Long id,Parcel parcel);

		void deleteParcel(Long id);
		
		Parcel findParcelById(Long id);
		
		List<Parcel> findAllParcel();
		
		List<Parcel> findParcelsByReceptionId(Long receptionId);
		
		List<Parcel> findParcelsByItemId(Long itemId);
}
