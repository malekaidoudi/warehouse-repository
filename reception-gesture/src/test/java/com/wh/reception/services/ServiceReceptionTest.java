package com.wh.reception.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
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
import com.wh.reception.domain.ItemLinePalette;
import com.wh.reception.domain.ItemLineParcel;
import com.wh.reception.domain.Palette;
import com.wh.reception.domain.Parcel;
import com.wh.reception.domain.Reception;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceReceptionTest {

	private static Server h2WebServer;
	private ServiceReception service;
	private EntityManager em;
	private EntityManagerFactory emf;

	@BeforeAll
	void startH2Console() throws SQLException {
//        if (Boolean.getBoolean("h2.console.enabled")) {
		h2WebServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
		System.out.println("Console H2 démarrée à : " + h2WebServer.getURL());
//       }
	}

	@AfterAll
	void stopH2Console() {
//       if (h2WebServer != null) {
		h2WebServer.stop();
		System.out.println("Console H2 arrêtée.");
//      }
	}

	@BeforeEach
	void setUp() {
		emf = Persistence.createEntityManagerFactory("UP_WAREHOUSE_TEST");
		em = emf.createEntityManager();
		service = new ServiceReceptionImp();
		((ServiceReceptionImp) service).setEntityManager(em);
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

		Palette palette = new Palette();
		palette.setReception(reception);
		palette.setHeight(100.0);
		palette.setWeight(50.0);
		palette.setDeliveryDate(LocalDate.now().plusDays(2));
		palette.setCreatedAt(new Date());
		palette.setUpdatedAt(new Date());
		reception.getPalettes().add(palette);

		em.getTransaction().begin();
		Reception savedReception = service.createReception(reception);
		em.getTransaction().commit();

		// pauseServer();

		return savedReception;
	}

	

	@Test
	@DisplayName("Créer une réception avec un colis")
	void testCreateReception() {
		Reception reception = createTestReception();
		assertNotNull(reception.getId(), "L'ID de la réception devrait être généré");
		assertNotNull(reception.getReference(), "La référence de la réception devrait être générée");
		// pauseServer();
		assertEquals(1, reception.getParcelsCount(), "La réception devrait contenir un colis");
	}

	

	@Test
	@DisplayName("Rechercher une réception par ID")
	void testFindReceptionById() {
		Reception reception = createTestReception();
		Reception found = service.findReceptionById(reception.getId());
		assertNotNull(found, "La réception devrait être trouvée");
		assertEquals(reception.getId(), found.getId(), "Les IDs devraient correspondre");
		assertEquals(reception.getReference(), found.getReference(), "Les références devraient correspondre");
	}

	@Test
	@DisplayName("Rechercher une réception par référence")
	void testFindReceptionByReference() {
		Reception reception = createTestReception();
		Reception found = service.findReceptionByReference(reception.getReference());
		assertNotNull(found, "La réception devrait être trouvée");
		assertEquals(reception.getReference(), found.getReference(), "Les références devraient correspondre");
	}

	@Test
	@DisplayName("Récupérer toutes les réceptions")
	void testFindAllReceptions() {
		createTestReception();
		List<Reception> receptions = service.findAllReceptions();
		assertFalse(receptions.isEmpty(), "La liste des réceptions ne devrait pas être vide");
	}

	@Test
	@DisplayName("Mettre à jour une réception")
	void testUpdateReception() {
		Reception reception = createTestReception();
		reception.setConsignee("UpdatedConsignee");
		em.getTransaction().begin();
		Reception updated = service.updateReception(reception);
		em.getTransaction().commit();
		assertEquals("UpdatedConsignee", updated.getConsignee(), "Le destinataire devrait être mis à jour");
	}

	@Test
	@DisplayName("Supprimer une réception")
	void testDeleteReception() {
		Reception reception = createTestReception();
		em.getTransaction().begin();
		service.deleteReception(reception.getId());
		em.getTransaction().commit();
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			service.findReceptionById(reception.getId());
		}, "La réception devrait être supprimée");
		assertEquals("Reception with ID " + reception.getId() + " not found", exception.getMessage());
	}

	@Test
	@DisplayName("Compter les colis d'une réception")
	void testGetParcelsCount() {
		Reception reception = createTestReception();
		int count = service.getParcelsCount(reception.getId());
		assertEquals(1, count, "La réception devrait contenir un colis");
	}

	@Test
	@DisplayName("Compter les palettes d'une réception")
	void testGetPalettesCount() {
		Reception reception = createTestReception();
		int count = service.getPalettesCount(reception.getId());
		assertEquals(1, count, "La réception ne devrait contenir aucune palette");
	}
	@Test
	@DisplayName("Trouver une réception par ID de palette")
	void testFindReceptionByPaletteId() {
		Reception reception = createTestReception();
		Palette palette = reception.getPalettes().get(0);
		Reception found = service.findReceptionByPaletteId(palette.getId());
		assertEquals(reception.getId(), found.getId(), "La réception devrait correspondre à celle de la palette");
	}
	
	@Test
	@DisplayName("Trouver une réception par ID de colis")
	void testFindReceptionByParcelId() {
		Reception reception = createTestReception();
		Parcel parcel = reception.getParcels().get(0);
		Reception found = service.findReceptionByParcelId(parcel.getId());
		assertEquals(reception.getId(), found.getId(), "La réception devrait correspondre à celle du colis");
	}

	@Test
	@DisplayName("Récupérer tous les colis d'une réception")
	void testFindAllParcels_Success() {
		Reception reception = createTestReception();
		Parcel parcel = reception.getParcels().get(0);

		List<Parcel> parcels = service.findAllParcels(reception.getId());

		assertNotNull(parcels, "La liste des colis ne devrait pas être nulle");
		assertEquals(1, parcels.size(), "La réception devrait contenir un colis");
		assertEquals(parcel.getId(), parcels.get(0).getId(), "L'ID du colis devrait correspondre");
	}

	@Test
	@DisplayName("Récupérer aucun colis pour une réception sans colis")
	void testFindAllParcels_EmptyList() {
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

		em.getTransaction().begin();
		service.createReception(reception);
		em.getTransaction().commit();
		//pauseServer();
		List<Parcel> parcels = service.findAllParcels(reception.getId());

		assertNotNull(parcels, "La liste des colis ne devrait pas être nulle");
		assertTrue(parcels.isEmpty(), "La liste des colis devrait être vide");
	}

	@Test
	@DisplayName("Échouer la récupération des colis avec un ID de réception nul")
	void testFindAllParcels_NullId() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			service.findAllParcels(null);
		});
		assertEquals("Reception ID cannot be null", exception.getMessage());
	}
	@Test
	@DisplayName("Récupérer toutes les palettes d'une réception")
	void testFindAllPalettes_Success() {
		Reception reception = createTestReception();
		Palette palette = reception.getPalettes().get(0);

		List<Palette> palettes = service.findAllPalettes(reception.getId());

		assertNotNull(palettes, "La liste des palettes ne devrait pas être nulle");
		assertEquals(1, palettes.size(), "La réception devrait contenir une palette");
		assertEquals(palette.getId(), palettes.get(0).getId(), "L'ID de la palette devrait correspondre");
	}

	@Test
	@DisplayName("Récupérer aucune palette pour une réception sans palette")
	void testFindAllPalettes_EmptyList() {
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
		service.createReception(reception);
		em.getTransaction().commit();
	
		//pauseServer();	
		List<Palette> palettes = service.findAllPalettes(reception.getId());
		assertNotNull(palettes, "La liste des palettes ne devrait pas être nulle");
		assertTrue(palettes.isEmpty(), "La liste des palettes devrait être vide");
		
	}
	@Test
	@DisplayName("Échouer la récupération des palettes avec un ID de réception nul")
	void testFindAllPalettes_NullId() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			service.findAllPalettes(null);
		});
		assertEquals("Reception ID cannot be null", exception.getMessage());
	}
	
	@Test
	@DisplayName("Récupérer les items d'une réception")
    void testFindItemsByReceptionId() {
        // Create items
        Item item1 = new Item("Item Test 1", "Test Description 1", 1.0);
        Item item2 = new Item("Item Test 2", "Test Description 2", 2.0);
        em.persist(item1);
        em.persist(item2);

        // Create reception
        Reception reception = createTestReception();
        
        em.getTransaction().begin();
        // Link items to parcel
        ItemLineParcel ilp1 = new ItemLineParcel(2);
        ilp1.setItem(item1);
        ilp1.setParcel(reception.getParcels().get(0));
        ilp1.setQuantity(10);
        em.persist(ilp1);

        // Link items to palette
        ItemLinePalette ilpal1 = new ItemLinePalette(3);
        ilpal1.setItem(item2);
        ilpal1.setPalette(reception.getPalettes().get(0));
        ilpal1.setQuantity(100);
        em.persist(ilpal1);

        em.getTransaction().commit();
        //pauseServer();
       
        List<Item> items = service.findItemsByReceptionId(reception.getId());
        assertEquals(2, items.size());
        assertTrue(items.containsAll(Arrays.asList(item1, item2)));
    }

    @Test
    @DisplayName("Récupérer aucun item pour une réception sans item")
    void testFindItemsByReceptionId_EmptyReception() {
        Reception reception = createTestReception();
        List<Item> items = service.findItemsByReceptionId(reception.getId());
        assertTrue(items.isEmpty());
    }

    @Test
    @DisplayName("Échouer la récupération des items avec un ID de réception inexistant")
    void testFindItemsByReceptionId_NonExistentReception() {
        List<Item> items = service.findItemsByReceptionId(999L);
        assertTrue(items.isEmpty());
    }

    @Test
    @DisplayName("Échouer la récupération des items avec un ID de réception nul")
    void testFindItemsByReceptionId_NullId() {
        assertThrows(IllegalArgumentException.class, () -> service.findItemsByReceptionId(null));
    }
	
}