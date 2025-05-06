package com.wh.reception.services;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import com.wh.reception.domain.Reception;
import com.wh.utilities.Locator;

public class ServiceReceptionTest {
	static ServiceReception serviceReception;

	@Before
	public void setUp() throws Exception {
		serviceReception = (ServiceReception) Locator.lookup("ServiceReceptionImp", ServiceReception.class);
	}

	@Test
	public void addReceptionTest() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2024, 11, 27);

		Reception reception = new Reception();
		reception.setDeliveryAddress("635 Fallview Terrace");
		calendar.set(2024, 05, 04);
		reception.setCreatedAt(calendar.getTime());
		reception.setUpdatedAt(calendar.getTime());
		calendar.set(2024, 05, 06);
		reception.setDeliveryDate(calendar.getTime());
		calendar.set(2024, 06, 8);
		reception.setExpirationDate(calendar.getTime());
		reception.setFragile(true);
		reception.setRotten(false);
		reception.setShippingAddress("6 Schurz Drive");
		reception.setWeight(357.0);
		reception.setReference(273963L);
		serviceReception.addReception(reception);

		Reception reception1 = new Reception();
		reception1.setDeliveryAddress("6 Bultman Road");
		calendar.set(2024, 07, 25);
		reception1.setCreatedAt(calendar.getTime());
		reception1.setUpdatedAt(calendar.getTime());
		calendar.set(2025, 03, 13);
		reception1.setDeliveryDate(calendar.getTime());
		calendar.set(2025, 05, 02);
		reception1.setExpirationDate(calendar.getTime());
		reception1.setFragile(false);
		reception1.setRotten(false);
		reception1.setShippingAddress("1 Laurel Way");
		reception1.setWeight(376.0);
		reception1.setReference(576963L);
		serviceReception.addReception(reception1);

		Reception reception2 = new Reception();
		reception2.setDeliveryAddress("94 Golden Leaf Circle");
		calendar.set(2024, 10, 07);
		reception2.setCreatedAt(calendar.getTime());
		reception2.setUpdatedAt(calendar.getTime());
		calendar.set(2025, 01, 25);
		reception2.setDeliveryDate(calendar.getTime());
		calendar.set(2025, 01, 29);
		reception2.setExpirationDate(calendar.getTime());
		reception2.setFragile(true);
		reception2.setRotten(true);
		reception2.setShippingAddress("2003 Portage Street");
		reception2.setWeight(496.0);
		reception2.setReference(986132L);

		serviceReception.addReception(reception2);

	}

	@Test
	public void getReceptionByIdTest() {
		Reception reception = serviceReception.getReceptionById(1);
		if (reception != null) {
			System.out.println("Reception found: " + reception);
		} else {
			System.out.println("Reception not found");
		}
	}

	@Test
	public void updateReceptionTest() {

		Reception reception = new Reception();
		reception.setId(3L);
		Calendar calendar = Calendar.getInstance();
		reception.setDeliveryAddress("40 Goodland Road");
		calendar.set(2024, 05, 04);
		reception.setCreatedAt(calendar.getTime());
		calendar.set(2024, 05, 06);
		reception.setDeliveryDate(calendar.getTime());
		calendar.set(2024, 06, 8);
		reception.setExpirationDate(calendar.getTime());
		reception.setFragile(true);
		reception.setRotten(false);
		reception.setShippingAddress("3 Canary Park");
		reception.setWeight(357.0);
		reception.setReference(621881L);
		serviceReception.updateReception(reception);

	}
	@Test
	public void deleteReceptionTest() {
		Reception reception = serviceReception.getReceptionById(3L);
		if (reception != null) {
			serviceReception.deleteReception(3L);
			System.out.println("Reception found: " + reception);
		} else {
			System.out.println("Reception not found");
		}
	}

}