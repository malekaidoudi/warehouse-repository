package com.wh.reception.services;

import java.util.List;

import com.wh.reception.domain.Dimension;

import jakarta.ejb.Remote;

@Remote
public interface ServiceDimension {
	
		/**
		 * 	CRUD operations for Dimension.
		 * @param dimension the dimension to be managed
		 * @param id the ID of the dimension
		 */

		Dimension addDimension(Dimension dimension);
		
		Dimension updateDimension(Long id,Dimension dimension);
		
		void deleteDimension(Long id);
		
		List<Dimension> findAllDimensions();
		
		Dimension findDimensionById(Long id);
		

}
