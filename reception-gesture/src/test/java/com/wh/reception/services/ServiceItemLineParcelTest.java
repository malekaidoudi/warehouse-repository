package com.wh.reception.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import org.h2.tools.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.wh.reception.domain.Item;
import com.wh.reception.domain.ItemLineParcel;
import com.wh.reception.domain.Parcel;
import com.wh.reception.domain.Reception;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceItemLineParcelTest {

	private static Server h2WebServer;
	private ServiceItemLineParcel service;
	private EntityManager em;
	private EntityManagerFactory emf;

	@BeforeAll
	void startH2Console() throws SQLException {
		// if (Boolean.getBoolean("h2.console.enabled")) {
		h2WebServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
		System.out.println("Console H2 démarrée à : " + h2WebServer.getURL());
		// }
	}

	@AfterAll
	void stopH2Console() {
		// if (h2WebServer != null) {
		h2WebServer.stop();
		System.out.println("Console H2 arrêtée.");
		// }
	}

	@BeforeEach
	void setUp() {
		emf = Persistence.createEntityManagerFactory("UP_WAREHOUSE_TEST");
		em = emf.createEntityManager();
		service = new ServiceItemLineParcelImp();
		((ServiceItemLineParcelImp) service).setEntityManager(em);
	}

	@AfterEach
	void tearDown() {
		if (em != null && em.isOpen()) {
			em.close();
		}
		if (emf != null && emf.isOpen()) {
			emf.close();
		}
	}

	private void pauseServer() {
		// Pause le serveur H2 pour permettre à l'utilisateur de voir les données
		System.out.println(
				"Test en pause. Consultez la console H2 à http://localhost:8082. Appuyez sur Entrée pour continuer...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Reception createTestReception() {
		Reception reception = new Reception("TestInitiator", "TestShiper", "TestConsignee",
				"123 Shipping St, 75001 Paris", "456 Delivery St, 75001 Paris");
		reception.setCreatedAt(new Date());
		reception.setUpdatedAt(new Date());

		Parcel parcel = new Parcel();
		parcel.setReception(reception);
		parcel.setWidth(100.0);
		parcel.setLength(200.0);
		parcel.setWeight(10.5);
		parcel.setDeliveryDate(LocalDate.now().plusDays(1));
		parcel.setCreatedAt(new Date());
		parcel.setUpdatedAt(new Date());
		reception.getParcels().add(parcel);

		em.getTransaction().begin();
		em.persist(reception);
		em.getTransaction().commit();

		// pauseServer();

		return reception;
	}
	
	@Test
	@DisplayName("Test de création d'une ligne de parcel")
	void testCreateItemLineParcel() {
		Reception reception = createTestReception();
		Parcel parcel = reception.getParcels().get(0);
		
		// Create item
		Item item1 = new Item("Test Item", "Test Description", 1.0);

		ItemLineParcel itemLineParcel = new ItemLineParcel(5);
		itemLineParcel.setItem(item1);
		itemLineParcel.setParcel(parcel);

		em.getTransaction().begin();
		em.persist(item1);
		em.persist(itemLineParcel);
		em.getTransaction().commit();

		assertNotNull(itemLineParcel.getId());
		assertEquals(5, itemLineParcel.getQuantity());
		assertEquals(parcel, itemLineParcel.getParcel());
		assertEquals("Test Item", itemLineParcel.getItem().getLabel());

		pauseServer();
	}

	@Test
	@DisplayName("Test de mise à jour d'une ligne de parcel")
	void testUpdateItemLineParcel() {
		Reception reception = createTestReception();
		Parcel parcel = reception.getParcels().get(0);
		
		// Create item
		Item item1 = new Item("Test Item", "Test Description", 1.0);

		ItemLineParcel itemLineParcel = new ItemLineParcel(5);
		itemLineParcel.setItem(item1);
		itemLineParcel.setParcel(parcel);

		em.getTransaction().begin();
		em.persist(item1);
		em.persist(itemLineParcel);
		em.getTransaction().commit();

		itemLineParcel.setQuantity(10);
		service.updateItemInParcel(item1.getId(), parcel.getId(), 10);

		assertEquals(10, itemLineParcel.getQuantity());

		pauseServer();
	}
	@Test
	@DisplayName("Test de suppression d'une ligne de parcel")
	void testDeleteItemLineParcel() {
		Reception reception = createTestReception();
		Parcel parcel = reception.getParcels().get(0);
		
		// Create item
		Item item1 = new Item("Test Item", "Test Description", 1.0);

		ItemLineParcel itemLineParcel = new ItemLineParcel(5);
		itemLineParcel.setItem(item1);
		itemLineParcel.setParcel(parcel);

		em.getTransaction().begin();
		em.persist(item1);
		em.persist(itemLineParcel);
		em.getTransaction().commit();

		service.deleteItemFromParcel(item1.getId(), parcel.getId());

		assertEquals(null, em.find(ItemLineParcel.class, itemLineParcel.getId()));

		pauseServer();
	}
	@Test
	@DisplayName("Test de recherche d'un article dans un parcel")
	void testFindItemInParcel() {
		Reception reception = createTestReception();
		Parcel parcel = reception.getParcels().get(0);
		
		// Create item
		Item item1 = new Item("Test Item", "Test Description", 1.0);

		ItemLineParcel itemLineParcel = new ItemLineParcel(5);
		itemLineParcel.setItem(item1);
		itemLineParcel.setParcel(parcel);

		em.getTransaction().begin();
		em.persist(item1);
		em.persist(itemLineParcel);
		em.getTransaction().commit();

		Item foundItem = service.findItemInParcel(item1.getId(), parcel.getId());

		assertNotNull(foundItem);
		assertEquals("Test Item", foundItem.getLabel());

		pauseServer();
	}
	
	@Test
	@DisplayName("Test de recherche de tous les articles dans un parcel")
	void testFindAllItemsInParcel() {
		Reception reception = createTestReception();
		Parcel parcel = reception.getParcels().get(0);
		
		// Create items
		Item item1 = new Item("Test Item 1", "Test Description 1", 1.0);
		Item item2 = new Item("Test Item 2", "Test Description 2", 2.0);

		ItemLineParcel itemLineParcel1 = new ItemLineParcel(5);
		itemLineParcel1.setItem(item1);
		itemLineParcel1.setParcel(parcel);

		ItemLineParcel itemLineParcel2 = new ItemLineParcel(10);
		itemLineParcel2.setItem(item2);
		itemLineParcel2.setParcel(parcel);

		em.getTransaction().begin();
		em.persist(item1);
		em.persist(item2);
		em.persist(itemLineParcel1);
		em.persist(itemLineParcel2);
		em.getTransaction().commit();

		assertEquals(2, service.findAllItemsInParcel(parcel.getId()).size());

		pauseServer();
	}

}
