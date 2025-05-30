package com.wh.reception.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
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
import com.wh.reception.domain.Palette;
import com.wh.reception.domain.Parcel;
import com.wh.reception.domain.Reception;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceItemTest {

	private static Server h2WebServer;
	private ServiceItem service;
	private ServiceReception serviceReception;
	private ServiceItemLinePalette servicePalette;
	private ServiceItemLineParcel serviceParcel;
	private EntityManager em;
	private EntityManagerFactory emf;

	@BeforeAll
	void startH2Console() throws SQLException {
       //if (Boolean.getBoolean("h2.console.enabled")) {
		h2WebServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
		System.out.println("Console H2 démarrée à : " + h2WebServer.getURL());
       // }
	}

	@AfterAll
	void stopH2Console() {
      // if (h2WebServer != null) {
		h2WebServer.stop();
		System.out.println("Console H2 arrêtée.");
        //}
	}

	@BeforeEach
	void setUp() {
		emf = Persistence.createEntityManagerFactory("UP_WAREHOUSE_TEST");
		em = emf.createEntityManager();
		service = new ServiceItemImp();
		((ServiceItemImp) service).setEntityManager(em);
		servicePalette = new ServiceItemLinePaletteImp();
		((ServiceItemLinePaletteImp) servicePalette).setEntityManager(em);
		serviceReception = new ServiceReceptionImp();
		((ServiceReceptionImp) serviceReception).setEntityManager(em);
		serviceParcel = new ServiceItemLineParcelImp();
		((ServiceItemLineParcelImp) serviceParcel).setEntityManager(em);
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
	
	private Reception createTestReception() {
		Reception reception = new Reception("TestInitiator", "TestShiper", "TestConsignee",
				"123 Shipping St, 75001 Paris", "456 Delivery St, 75001 Paris");
		reception.setCreatedAt(new Date());
		reception.setUpdatedAt(new Date());

		Palette palette = new Palette();
		palette.setReception(reception);
		palette.setHeight(100.0);
		palette.setWeight(50.0);
		palette.setDeliveryDate(LocalDate.now().plusDays(2));
		palette.setCreatedAt(new Date());
		palette.setUpdatedAt(new Date());
		reception.getPalettes().add(palette);
		
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
		Reception savedReception = serviceReception.createReception(reception);
		em.getTransaction().commit();

		// pauseServer();

		return savedReception;
	}


//	private void pauseServer() {
//		// Pause le serveur H2 pour permettre à l'utilisateur de consulter la console
//		System.out.println(
//				"Test en pause. Consultez la console H2 à http://localhost:8082. Appuyez sur Entrée pour continuer...");
//		try {
//			System.in.read();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
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
		
		//pauseServer();
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
		
		//pauseServer();
		
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
		
		//pauseServer();
		
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
	@DisplayName("Rechercher un élément par ID")
	void testFindItemById() {
		Item item = new Item();
		item.setLabel("Test Item");
		item.setDescription("Test Description");
		item.setWeight(1.0);
		
		em.getTransaction().begin();
		service.addItem(item);
		em.getTransaction().commit();
		
		Item foundItem = service.findItemById(item.getId());
		
		assertNotNull(foundItem, "L'élément n'a pas été trouvé dans la base de données");
		assertEquals("Test Item", foundItem.getLabel(), "L'étiquette de l'élément ne correspond pas");
		assertEquals("Test Description", foundItem.getDescription(), "La description de l'élément ne correspond pas");
		assertEquals(1.0, foundItem.getWeight(), "Le poids de l'élément ne correspond pas");
		
		//pauseServer();
		
		Item notFoundItem = service.findItemById(item.getId() + 1);
		assertEquals(null, notFoundItem, "L'élément n'aurait pas dû être trouvé");
		
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
		
		//pauseServer();
		
	}
	
	@Test
	@DisplayName("Rechercher des éléments par ID de palette")
	void testFindItemsByPaletteId() {
	   
	    Reception reception = createTestReception(); 
	    Palette palette = reception.getPalettes().get(0);
	    
	    // Créer et persister Item1
	    Item item1 = new Item();
	    item1.setLabel("Test Item 1");
	    item1.setDescription("Test Description 1");
	    item1.setWeight(1.0);
	    
	    // Créer et persister Item2
	    Item item2 = new Item();
	    item2.setLabel("Test Item 2");
	    item2.setDescription("Test Description 2");
	    item2.setWeight(2.0);
	    
	    em.getTransaction().begin();
	    service.addItem(item1);
	    service.addItem(item2); // Persiste item2
	    servicePalette.addItemToPalette(item1.getId(), palette.getId(), 10);
	    servicePalette.addItemToPalette(item2.getId(), palette.getId(), 20);
	    em.getTransaction().commit();

	    // Associer item2 à la palette
	    
	    System.out.println("Palette ID: " + palette.getId());
	    System.out.println("Item1 ID: " + item1.getId());
	    System.out.println("Item2 ID: " + item2.getId());
	    // Tester la recherche
	    List<Item> items = service.findItemsByPaletteId(palette.getId());
	    assertNotNull(items, "La liste des éléments ne doit pas être nulle");
	    assertEquals(2, items.size(), "La liste des éléments doit contenir deux éléments pour la palette ID " + palette.getId());

	    // Tester avec une palette inexistante
	    List<Item> emptyItems = service.findItemsByPaletteId(palette.getId() + 1); // ID de palette qui n'existe pas
	    assertEquals(0, emptyItems.size(), "La liste des éléments pour la palette ID 200 doit être vide");
	    
	    //pauseServer();
	}
	
	
	
	
	@Test
	@DisplayName("Rechercher des éléments par ID de parcel")
	void testFindItemsByParcelId() {
		Reception reception = createTestReception();
		Parcel parcel = reception.getParcels().get(0);
		
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
		serviceParcel.addItemToParcel(item1.getId(), parcel.getId(), 10);
		serviceParcel.addItemToParcel(item2.getId(), parcel.getId(), 20);
		em.getTransaction().commit();
		
		List<Item> items = service.findItemsByParcelId(parcel.getId());
		
		assertNotNull(items, "La liste des éléments ne doit pas être nulle");
		assertEquals(2, items.size(), "La liste des éléments doit contenir deux éléments pour le parcel ID " + parcel.getId());
		
		List<Item> emptyItems = service.findItemsByParcelId(parcel.getId() + 1); // ID de parcel qui n'existe pas
		assertEquals(0, emptyItems.size(), "La liste des éléments pour le parcel ID inexistant doit être vide");
		
		
		//pauseServer();
		
	}
	
	@Test
	@DisplayName("Rechercher des éléments par ID de réception")
	void testFindItemsByReceptionId() {
		Reception reception = createTestReception();
		
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
		serviceParcel.addItemToParcel(item1.getId(), reception.getParcels().get(0).getId(), 10);
		servicePalette.addItemToPalette(item2.getId(), reception.getPalettes().get(0).getId(), 20);
		em.getTransaction().commit();
		
		List<Item> items = service.findItemsByReceptionId(reception.getId());
		
		assertNotNull(items, "La liste des éléments ne doit pas être nulle");
		assertEquals(2, items.size(), "La liste des éléments doit contenir deux éléments pour la réception ID " + reception.getId());
		
		List<Item> emptyItems = service.findItemsByReceptionId(reception.getId() + 1); // ID de réception qui n'existe pas
		assertEquals(0, emptyItems.size(), "La liste des éléments pour la réception ID inexistant doit être vide");
		
		//pauseServer();
		
	}	

}
