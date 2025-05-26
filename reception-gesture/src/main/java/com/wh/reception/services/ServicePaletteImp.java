package com.wh.reception.services;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.wh.reception.domain.Palette;
import com.wh.reception.domain.Reception;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
@Stateless
public class ServicePaletteImp implements ServicePalette {
	// palette without items
	Logger logger = Logger.getLogger("RECEPTION_SERVICE");

	@PersistenceContext(unitName = "UP_WAREHOUSE")
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
@Override
	public void addPalette(Palette palette) {
		if (palette.getReception() == null) {
			throw new IllegalArgumentException("A palette must be associated with a valid reception.");
		}
		Reception reception = em.find(Reception.class, palette.getReception().getId());
		if (reception == null) {
			throw new IllegalArgumentException("The specified reception does not exist.");
		}
		palette.validate();
		reception.getPalettes().add(palette);
		em.persist(palette);
		logger.info("Successfully added palette with id: " + palette.getId());
	}

	@Override
	public void updatePalette(Palette palette) {
		if (palette == null || palette.getId() == null) {
			throw new IllegalArgumentException("Palette or its ID cannot be null");
		}
		Palette existing = em.find(Palette.class, palette.getId());
		if (existing == null) {
			throw new IllegalArgumentException("Palette with ID " + palette.getId() + " not found");
		}
		Reception reception = em.find(Reception.class, palette.getReception().getId());
		if (reception == null) {
			throw new IllegalArgumentException("The specified reception does not exist.");
		}
		palette.validate();
		palette.setUpdatedAt(new Date());
		em.merge(palette);
		logger.info("Successfully updated palette with id: " + palette.getId());
	}

	@Override
	public void deletePalette(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Palette ID cannot be null");
		}
		Palette palette = em.find(Palette.class, id);
		if (palette == null) {
			throw new IllegalArgumentException("Palette with ID " + id + " not found");
		}
		Reception reception = palette.getReception();
		if (reception != null) {
			reception.getPalettes().remove(palette);
		}
		em.remove(palette);
		logger.info("Successfully deleted palette with id: " + id);
	}

	@Override
	public Palette findPaletteById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Palette ID cannot be null");
		}
		Palette palette = em.find(Palette.class, id);
		if (palette == null) {
			throw new IllegalArgumentException("Palette with ID " + id + " not found");
		}
		return palette;
	}
	@Override
	public List<Palette> findAllPalettes(Long receptionId) {
		
		    if (receptionId == null) {
		        throw new IllegalArgumentException("Reception ID cannot be null");
		    }
		    TypedQuery<Palette> query = em.createQuery(
		        "SELECT p FROM Palette p WHERE p.reception.id = :receptionId", Palette.class);
		    query.setParameter("receptionId", receptionId);
		    
		    List<Palette> palettes = query.getResultList();
		    
		    if (palettes.isEmpty()) {
		        logger.warning("No palettes found for reception ID: " + receptionId);
		    } else {
		        logger.info("Found " + palettes.size() + " palettes for reception ID: " + receptionId);
		    }
		    return palettes;
		}

}
