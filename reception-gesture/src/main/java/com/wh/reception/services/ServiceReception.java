package com.wh.reception.services;

import com.wh.reception.domain.Reception;

import jakarta.ejb.Remote;

@Remote
public interface ServiceReception {
	//reception
		 void addReception(Reception reception);
		 void updateReception(Reception reception);
		 Reception getReceptionById(long id);
		 void deleteReception(long id);

}
