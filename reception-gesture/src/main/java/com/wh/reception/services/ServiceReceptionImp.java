package com.wh.reception.services;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.wh.reception.domain.Item;
import com.wh.reception.domain.Palette;
import com.wh.reception.domain.Parcel;
import com.wh.reception.domain.Reception;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class ServiceReceptionImp implements ServiceReception {

	Logger logger = Logger.getLogger("RECEPTION_SERVICE");

	@PersistenceContext(unitName = "UP_WAREHOUSE")
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override		
	public Reception createReception(Reception reception) {
		if (reception.getParcels().isEmpty() && reception.getPalettes().isEmpty()) {
			throw new IllegalStateException("Reception must have at least one parcel or palette");
		}

		if (reception.getCreatedAt() == null) {
			reception.setCreatedAt(new Date());
			reception.setUpdatedAt(new Date());
		}

		// Définir un reference temporaire pour satisfaire la contrainte NOT NULL
		reception.setReference(0L);

		logger.info("Avant persistance : reference=" + reception.getReference());
		em.persist(reception);
	
		return reception;
	}
	@Override
	public Reception updateReception(Reception reception) {
		if (reception == null || reception.getId() == null) {
			throw new IllegalArgumentException("Reception or its ID cannot be null");
		}
		reception.validate();
		Reception existing = em.find(Reception.class, reception.getId());
		if (existing == null) {
			throw new IllegalArgumentException("Reception with ID " + reception.getId() + " not found");
		}
		reception.setUpdatedAt(new Date());
		return em.merge(reception);
	}
	@Override

	public void deleteReception(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Reception ID cannot be null");
		}
		Reception reception = em.find(Reception.class, id);
		if (reception == null) {
			throw new IllegalArgumentException("Reception with ID " + id + " not found");
		}
		em.remove(reception);
	}

	@Override
	public Reception findReceptionById(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Reception ID cannot be null");
		}
		Reception reception = em.find(Reception.class, id);
		if (reception == null) {
			throw new IllegalArgumentException("Reception with ID " + id + " not found");
		}
		return reception;
	}

	@Override
	public Reception findReceptionByReference(Long reference) {
		if (reference == null) {
			throw new IllegalArgumentException("Reception reference cannot be null");
		}
		TypedQuery<Reception> query = em.createQuery("SELECT r FROM Reception r WHERE r.reference = :reference",
				Reception.class);
		query.setParameter("reference", reference);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new IllegalArgumentException("Reception with reference " + reference + " not found");
		}
	}

	@Override
	public Reception findReceptionByPaletteId(Long paletteId) {
		if (paletteId == null) {
			throw new IllegalArgumentException("Palette ID cannot be null");
		}
		Palette palette = em.find(Palette.class, paletteId);
		if (palette == null) {
			throw new IllegalArgumentException("Palette with ID " + paletteId + " not found");
		}
		return palette.getReception();
	}

	@Override
	public Reception findReceptionByParcelId(Long parcelId) {
		if (parcelId == null) {
			throw new IllegalArgumentException("Parcel ID cannot be null");
		}
		Parcel parcel = em.find(Parcel.class, parcelId);
		if (parcel == null) {
			throw new IllegalArgumentException("Parcel with ID " + parcelId + " not found");
		}
		return parcel.getReception();
	}

	@Override
	public List<Palette> findAllPalettes(Long receptionId) {
		if (receptionId == null) {
			throw new IllegalArgumentException("Reception ID cannot be null");
		}
		TypedQuery<Palette> query = em.createQuery(
				"SELECT p FROM Palette p WHERE p.reception.id = :receptionId", Palette.class);
		query.setParameter("receptionId", receptionId);
		List<Palette> palettes= query.getResultList();
		if (palettes.isEmpty()) {
			logger.info("No palettes found for reception ID " + receptionId);
		} else {
			logger.info("Palettes found for reception ID " + receptionId + ": " + palettes.size());
		}
		return palettes;
	}
	
	@Override
	public List<Parcel> findAllParcels(Long receptionId) {

		    if (receptionId == null) {
		        throw new IllegalArgumentException("Reception ID cannot be null");
		    }
		    TypedQuery<Parcel> query = em.createQuery(
		        "SELECT p FROM Parcel p WHERE p.reception.id = :receptionId", Parcel.class);
		    query.setParameter("receptionId", receptionId);
		    
		    List<Parcel> parcels = query.getResultList();
		    
		    if (parcels.isEmpty()) {
		        logger.info("No parcels found for reception ID " + receptionId);
		    } else {
		        logger.info("Parcels found for reception ID " + receptionId + ": " + parcels.size());
		    }
		    return parcels;
		}
	@Override
	public List<Item> findItemsByReceptionId(Long receptionId) {
        if (receptionId == null) {
            throw new IllegalArgumentException("Reception ID cannot be null");
        }

        TypedQuery<Item> query = em.createQuery(
            "SELECT DISTINCT i " +
            "FROM Item i " +
            "WHERE EXISTS (" +
            "    SELECT 1 FROM Reception r " +
            "    JOIN r.parcels p " +
            "    JOIN p.itemLineParcels ilp " +
            "    WHERE ilp.item = i AND r.id = :receptionId" +
            ") OR EXISTS (" +
            "    SELECT 1 FROM Reception r " +
            "    JOIN r.palettes pal " +
            "    JOIN pal.itemLinePalettes ilpal " +
            "    WHERE ilpal.item = i AND r.id = :receptionId" +
            ")",
            Item.class
        );
        query.setParameter("receptionId", receptionId);
        return query.getResultList();
    }

//	@Override
//	public List<Item> findItemsByReceptionId(Long receptionId) {
//		if (receptionId == null) {
//			throw new IllegalArgumentException("Reception ID cannot be null");
//		}
//		 String req = "SELECT i FROM Item i "
//				+ "JOIN i.palettes p WHERE p.reception.id = :receptionId "
//				+ "UNION "
//				+ "SELECT i FROM Item i "
//				+ "JOIN i.parcels pa WHERE pa.reception.id = :receptionId";
//		List <Item> items = em.createQuery(req, Item.class)
//				.setParameter("receptionId", receptionId)
//				.getResultList();
//		if (items.isEmpty()) {
//			logger.info("No items found for reception ID " + receptionId);
//		} else {
//			logger.info("Items found for reception ID " + receptionId + ": " + items.size());
//		}
//		return items;
//	}
	
	@Override
	public List<Reception> findAllReceptions() {
		TypedQuery<Reception> query = em.createQuery("SELECT r FROM Reception r", Reception.class);
		return query.getResultList();
	}

	@Override
	public int getParcelsCount(Long receptionId) {
		Reception reception = findReceptionById(receptionId);
		return reception.getParcelsCount();
	}

	@Override
	public int getPalettesCount(Long receptionId) {
		Reception reception = findReceptionById(receptionId);
		return reception.getPalettesCount();
	}
	
	

	

	
	

	
}