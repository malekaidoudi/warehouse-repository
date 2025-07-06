package com.wh.reception.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import com.wh.reception.domain.ItemLinePalette;
import com.wh.reception.domain.ItemLinePalettePK;
import com.wh.reception.domain.Palette;
import com.wh.reception.domain.Reception;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceItemLinePaletteTest {

	private static Server h2WebServer;
	private ServiceItemLinePalette service;
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
		service = new ServiceItemLinePaletteImp();
		((ServiceItemLinePaletteImp) service).setEntityManager(em);
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

//	private void pauseServer() {
//		// Pause le serveur H2 pour permettre à l'utilisateur de voir les données
//		System.out.println(
//				"Test en pause. Consultez la console H2 à http://localhost:8082. Appuyez sur Entrée pour continuer...");
//		try {
//			System.in.read();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	private Reception createTestReception() {
		Reception reception = new Reception("TestInitiator", "TestShiper", "TestConsignee",
				"123 Shipping St, 75001 Paris", "456 Delivery St, 75001 Paris");
		reception.setCreatedAt(new Date());
		reception.setUpdatedAt(new Date());

		// Create palette
		Palette palette = new Palette();
		palette.setReception(reception);
		palette.setHeight(100.0);
		palette.setWeight(50.0);
		palette.setDeliveryDate(LocalDate.now().plusDays(2));
		palette.setCreatedAt(new Date());
		palette.setUpdatedAt(new Date());
		reception.getPalettes().add(palette);

		

		em.getTransaction().begin();
		em.persist(reception);
		em.getTransaction().commit();

		// pauseServer();

		return reception;
	}

	@Test
	@DisplayName("Test d'ajout d'un article à une palette")
	void testAddItemToPalette() {
		

		Reception reception = createTestReception();
		Palette palette = reception.getPalettes().get(0);
		
		
		// Create items
		Item item1 = new Item("Test Item", "Test Description", 1.0);
		
		// Link items to palette
		ItemLinePalette ilpal1 = new ItemLinePalette(10);
		ilpal1.setItem(item1);
		ilpal1.setPalette(palette);
		
		em.getTransaction().begin();
		em.persist(item1);
		em.persist(ilpal1);
		em.getTransaction().commit();

		ItemLinePalette itemLinePalette = em.find(ItemLinePalette.class,
				new ItemLinePalettePK(item1.getId(), palette.getId()));
		assertNotNull(itemLinePalette);
		assertEquals(10, itemLinePalette.getQuantity());

		//pauseServer();
	}

	@Test
	@DisplayName("Test de mise à jour d'un article dans une palette")
	void testUpdateItemInPalette() {
		
		Reception reception = createTestReception();
		Palette palette = reception.getPalettes().get(0);
		

		// Create items
		Item item1 = new Item("Test Item", "Test Description", 1.0);

		// Link items to palette
		ItemLinePalette ilpal1 = new ItemLinePalette(10);
		ilpal1.setItem(item1);
		ilpal1.setPalette(palette);
		
		em.getTransaction().begin();
		em.persist(item1);
		em.persist(ilpal1);
		em.getTransaction().commit();

		ItemLinePalette itemLinePalette = em.find(ItemLinePalette.class,
				new ItemLinePalettePK(item1.getId(), palette.getId()));

		// Update the quantity
		em.getTransaction().begin();
		itemLinePalette.setQuantity(20);
		em.merge(itemLinePalette);
		em.getTransaction().commit();

		// Verify the update
		ItemLinePalette updatedItemLinePalette = em.find(ItemLinePalette.class,
				new ItemLinePalettePK(item1.getId(), palette.getId()));
		assertNotNull(updatedItemLinePalette);
		assertEquals(20, updatedItemLinePalette.getQuantity());

		//pauseServer();
	}

	@Test
	@DisplayName("Test de suppression d'un article d'une palette")
	void testDeleteItemFromPalette() {
		
		Reception reception = createTestReception();
		Palette palette = reception.getPalettes().get(0);
		
		

		// Create items
		Item item1 = new Item("Test Item", "Test Description", 1.0);

		// Link items to palette
		ItemLinePalette ilpal1 = new ItemLinePalette(10);
		ilpal1.setItem(item1);
		ilpal1.setPalette(palette);
		
		em.getTransaction().begin();
		em.persist(item1);
		em.persist(ilpal1);
		em.getTransaction().commit();
		
		
		ItemLinePalette itemLinePalette = em.find(ItemLinePalette.class,
				new ItemLinePalettePK(item1.getId(), palette.getId()));
		assertNotNull(itemLinePalette);
		assertEquals(10, itemLinePalette.getQuantity());
		
		// Delete the item from the palette
		em.getTransaction().begin();
		em.remove(itemLinePalette);
		em.getTransaction().commit();
		
		// Verify the deletion
		ItemLinePalette deletedItemLinePalette = em.find(ItemLinePalette.class,
				new ItemLinePalettePK(item1.getId(), palette.getId()));
		System.out.println("deletedItemLinePalette : " + deletedItemLinePalette);
		
		assertEquals(null, deletedItemLinePalette);
		
		//pauseServer();
	}
	@Test
	@DisplayName("Lister tout les articles dans une palette")
	void testFindAllItemsInPalette() {
		
		Reception reception = createTestReception();
		Palette palette = reception.getPalettes().get(0);
		
		

		// Create items
		Item item1 = new Item("Test Item", "Test Description", 1.0);
		Item item2 = new Item("Test Item 2", "Test Description 2", 2.0);

		// Link items to palette
		ItemLinePalette ilpal1 = new ItemLinePalette(10);
		ilpal1.setItem(item1);
		ilpal1.setPalette(palette);
		
		ItemLinePalette ilpal2 = new ItemLinePalette(20);
		ilpal2.setItem(item2);
		ilpal2.setPalette(palette);
		
		em.getTransaction().begin();
		em.persist(item1);
		em.persist(item2);
		em.persist(ilpal1);
		em.persist(ilpal2);
		em.getTransaction().commit();

		
		assertEquals(2, service.findAllItemsInPalette(palette.getId()).size());
		
		//pauseServer();
		
	}

}
