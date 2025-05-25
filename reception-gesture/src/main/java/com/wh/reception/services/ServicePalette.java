package com.wh.reception.services;

import java.util.List;

import com.wh.reception.domain.Palette;

public interface ServicePalette {
	
		/**
		 * 	CRUD operations for Palette.
		 * @param palette the palette to be managed
		 * @param id the ID of the palette
		 * @param receptionId the ID of the reception
		 * 
		 */
	
	
		void addPalette(Palette palette);

		void updatePalette(Palette palette);

		void deletePalette(Long id);
		
		Palette findPaletteById(Long id);
		
		List<Palette> findAllPalettes(Long receptionId);

}
