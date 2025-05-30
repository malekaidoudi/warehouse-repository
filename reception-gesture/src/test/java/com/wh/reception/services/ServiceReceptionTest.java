package com.wh.reception.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	
}