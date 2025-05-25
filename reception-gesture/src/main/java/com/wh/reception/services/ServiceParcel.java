package com.wh.reception.services;

import java.util.List;

import com.wh.reception.domain.Parcel;

public interface ServiceParcel {

	/**
	 * CRUD operations for Parcel.
	 * @param parcel the parcel to be managed
	 * @param id the ID of the parcel
	 * @param receptionId the ID of the reception
	 * 
	 */

		void addParcel(Parcel parcel);

		void updateParcel(Parcel parcel);

		void deleteParcel(Long id);
		
		Parcel findParcelById(Long id);
		
		List<Parcel> findAllParcel(Long receptionId);
}
