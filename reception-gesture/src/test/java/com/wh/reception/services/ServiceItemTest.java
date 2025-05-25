package com.wh.reception.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.h2.tools.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.wh.reception.domain.Item;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceItemTest {

	private static Server h2WebServer;
	private ServiceItem service;
	private EntityManager em;
	private EntityManagerFactory emf;

	@BeforeAll
	void startH2Console() throws SQLException {
       if (Boolean.getBoolean("h2.console.enabled")) {
		h2WebServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
		System.out.println("Console H2 démarrée à : " + h2WebServer.getURL());
        }
	}

	@AfterAll
	void stopH2Console() {
       if (h2WebServer != null) {
		h2WebServer.stop();
		System.out.println("Console H2 arrêtée.");
        }
	}

	@BeforeEach
	void setUp() {
		emf = Persistence.createEntityManagerFactory("UP_WAREHOUSE_TEST");
		em = emf.createEntityManager();
		service = new ServiceItemImp();
		((ServiceItemImp) service).setEntityManager(em);
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
		// Pause le serveur H2 pour permettre à l'utilisateur de consulter la console
		System.out.println(
				"Test en pause. Consultez la console H2 à http://localhost:8082. Appuyez sur Entrée pour continuer...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("Ajouter un élément")
	void testAddItem() {
		Item item = new Item();
		
		item.setLabel(null);
		item.setDescription("Test Description");
		item.setWeight(1.0);
		
		try {
			service.addItem(item);
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			assertEquals("Item label cannot be empty or null.", e.getMessage());
		}
		
		item.setLabel("");
		item.setDescription("Test Description");
		item.setWeight(1.0);
		
		try {
			service.addItem(item);
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			assertEquals("Item label cannot be empty or null.", e.getMessage());
		}
		
		item.setLabel("Test Item");
		item.setDescription(null);
		item.setWeight(1.0);
		try {
			service.addItem(item);
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			assertEquals("Item description cannot be empty or null.", e.getMessage());
		}
		
		item.setLabel("Test Item");
		item.setDescription("");
		item.setWeight(1.0);
		try {
			service.addItem(item);
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			assertEquals("Item description cannot be empty or null.", e.getMessage());
		}
		
		item.setLabel("Test Item");
		item.setDescription("Test Description");
		item.setWeight(-1.0);
		try {
			service.addItem(item);
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			assertEquals("Weight must be positive.", e.getMessage());
		}
		
		item.setLabel("Test Item");
		item.setDescription("Test Description");
		item.setWeight(1.0);
		
		em.getTransaction().begin();
		service.addItem(item);
		em.getTransaction().commit();
		
		
		Item foundItem = em.find(Item.class, item.getId());
		assertEquals("Test Item", foundItem.getLabel(), "L'étiquette de l'élément ne correspond pas");
		assertEquals("Test Description", foundItem.getDescription(), "La description de l'élément ne correspond pas");
		assertEquals(1.0, foundItem.getWeight(), "Le poids de l'élément ne correspond pas");
		
		pauseServer();
	}

	@Test
	@DisplayName("Mettre à jour un élément")
	void testUpdateItem() {
		Item item = new Item();
		item.setLabel("Test Item");
		item.setDescription("Test Description");
		item.setWeight(1.0);
		
		em.getTransaction().begin();
		service.addItem(item);
		em.getTransaction().commit();
		
		item.setLabel("Updated Item");
		item.setDescription("Updated Description");
		item.setWeight(2.0);
		
		em.getTransaction().begin();
		service.updateItem(item);
		em.getTransaction().commit();
		
		Item updatedItem = em.find(Item.class, item.getId());
		assertNotNull(updatedItem, "L'élément mis à jour n'a pas été trouvé dans la base de données");
		assertEquals("Updated Item", updatedItem.getLabel(), "L'étiquette de l'élément mis à jour ne correspond pas");
		assertEquals("Updated Description", updatedItem.getDescription(), "La description de l'élément mis à jour ne correspond pas");
		assertEquals(2.0, updatedItem.getWeight(), "Le poids de l'élément mis à jour ne correspond pas");
		
		pauseServer();
		
		item.setId(item.getId()+1L);
		try {
			em.getTransaction().begin();
			service.updateItem(item);
			em.getTransaction().commit();
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			assertEquals("Item with ID " + item.getId() + " not found.", e.getMessage());
		}
		
	}

	@Test
	@DisplayName("Supprimer un élément")
	void testDeleteItem() {
		Item item = new Item();
		item.setLabel("Test Item");
		item.setDescription("Test Description");
		item.setWeight(1.0);
		
		em.getTransaction().begin();
		service.addItem(item);
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		service.deleteItem(item.getId());
		em.getTransaction().commit();
		
		Item deletedItem = em.find(Item.class, item.getId());
		assertEquals(null, deletedItem, "L'élément n'a pas été supprimé de la base de données");
		
		pauseServer();
		
		try {
			em.getTransaction().begin();
			service.deleteItem(item.getId());
			em.getTransaction().commit();
			fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			assertEquals("Item with ID " + item.getId() + " not found", e.getMessage());
		}
		
	}

	@Test
	@DisplayName("Rechercher tous les éléments")
	void testFindAllItems() {
		Item item1 = new Item();
		item1.setLabel("Test Item 1");
		item1.setDescription("Test Description 1");
		item1.setWeight(1.0);
		
		Item item2 = new Item();
		item2.setLabel("Test Item 2");
		item2.setDescription("Test Description 2");
		item2.setWeight(2.0);
		
		em.getTransaction().begin();
		service.addItem(item1);
		service.addItem(item2);
		em.getTransaction().commit();
		
		List<Item> items = service.findAllItems();
		
		assertNotNull(items, "La liste des éléments ne doit pas être nulle");
		assertEquals(2, items.size(), "La liste des éléments doit contenir deux éléments");
		
		pauseServer();
		
	}

//	@Test
//	@DisplayName("Rechercher un élément par ID")
//	void testFindItemsByReceptionId() {
//		Item item1 = new Item();
//		item1.setLabel("Test Item 1");
//		item1.setDescription("Test Description 1");
//		item1.setWeight(1.0);
//		
//		Item item2 = new Item();
//		item2.setLabel("Test Item 2");
//		item2.setDescription("Test Description 2");
//		item2.setWeight(2.0);
//		
//		em.getTransaction().begin();
//		service.addItem(item1);
//		service.addItem(item2);
//		em.getTransaction().commit();
//		
//		
//		
//		
//				
//		pauseServer();
//	}

}
