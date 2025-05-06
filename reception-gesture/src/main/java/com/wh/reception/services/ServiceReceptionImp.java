package com.wh.reception.services;

import java.util.Date;
import java.util.logging.Logger;

import com.wh.reception.domain.Reception;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Session Bean implementation class ServiceDao
 */
@Stateless
public class ServiceReceptionImp implements ServiceReception {

 Logger logger=Logger.getLogger ("'RECEPTION_SERVICE");
    @PersistenceContext(unitName = "UP_WAREHOUSE")
    private EntityManager em;
    

    @Override
    public void addReception(Reception reception) {
        em.persist(reception);
        logger.info (" successfully added reception with id: " + reception.getId());
    }
    
    @Override
    public void updateReception(Reception reception) {
    	Reception r=em.find(Reception.class, reception.getId());
    	if (r==null) {
    		throw new IllegalArgumentException("Reception not found");
		}else {
			reception.setUpdatedAt(new Date());
        	em.merge(reception);
        	logger.info (" successfully updated reception with id: " + reception.getId());
		}
    }
	@Override
	public Reception getReceptionById(long id) {
		Reception reception = em.find(Reception.class, id);
		return reception;
	}

	@Override
	public void deleteReception(long id) {
		Reception reception = em.find(Reception.class, id);
		if (reception != null) {
			em.remove(reception);
			logger.info (" successfully deleted reception with id: " + reception.getId());
		} else {
			throw new IllegalArgumentException("Reception not found");
		}
		
	}

	}
