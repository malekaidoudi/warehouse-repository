package com.wh.reception.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import com.wh.reception.domain.Palette;
import com.wh.reception.domain.Parcel;
import com.wh.reception.domain.Reception;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceParcelTest {

	private static Server h2WebServer;
	private ServiceReception serviceReception;
	private ServiceParcel service;
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
		serviceReception = new ServiceReceptionImp();
		((ServiceReceptionImp) serviceReception).setEntityManager(em);
		service = new ServiceParcelImp();
		((ServiceParcelImp) service).setEntityManager(em);
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
//		// Pause le serveur H2 pour permettre à l'utilisateur de consulter la base de données
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
		Reception savedReception = serviceReception.createReception(reception);
		em.getTransaction().commit();

		 //pauseServer();

		return savedReception;
	}
	
	@Test
	@DisplayName("Ajouter un colis")
	void testAddParcel() {
		Reception reception = createTestReception();
		Parcel parcel = new Parcel();
		parcel.setReception(reception);
		parcel.setWidth(150.0);
		parcel.setLength(250.0);
		parcel.setWeight(15.0);
		parcel.setDeliveryDate(LocalDate.now().plusDays(2));
		parcel.setCreatedAt(new Date());
		parcel.setUpdatedAt(new Date());
		em.getTransaction().begin();
		service.addParcel(parcel);
		em.getTransaction().commit();
		assertNotNull(parcel.getId(), "L'ID du colis devrait être généré");
		assertEquals(2, serviceReception.getParcelsCount(reception.getId()), "La réception devrait contenir deux colis");
	}

	@Test
	@DisplayName("Mettre à jour un colis")
	void testUpdateParcel() {
		Reception reception = createTestReception();
		Parcel parcel = reception.getParcels().get(0);
		parcel.setWeight(20.0);
		em.getTransaction().begin();
		service.updateParcel(parcel);
		em.getTransaction().commit();
		Parcel updated = em.find(Parcel.class, parcel.getId());
		assertEquals(20.0, updated.getWeight(), 0.01, "Le poids du colis devrait être mis à jour");
	}

	@Test
	@DisplayName("Supprimer un colis")
	void testDeleteParcel() {
		Reception reception = createTestReception();
		Parcel parcel = reception.getParcels().get(0);
		em.getTransaction().begin();
		service.deleteParcel(parcel.getId());
		em.getTransaction().commit();
		em.clear();
		Parcel deleted = em.find(Parcel.class, parcel.getId());
		assertNull(deleted, "Le colis devrait être supprimé");
		assertEquals(0, serviceReception.getParcelsCount(reception.getId()), "La réception ne devrait plus contenir de colis");
	}

	@Test
	@DisplayName("Échouer la suppression d'un colis inexistant")
	void testDeleteNonExistentParcel() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			em.getTransaction().begin();
			service.deleteParcel(999L);
			em.getTransaction().commit();
		});
		assertEquals("Parcel with ID 999 not found", exception.getMessage());
	}
	@Test
	@DisplayName("Récupérer un colis par ID")
	void testFindParcelById_Success() {
		Reception reception = createTestReception();
		Parcel parcel = reception.getParcels().get(0);

		Parcel found = service.findParcelById(parcel.getId());

		assertNotNull(found, "Le colis devrait être trouvé");
		assertEquals(parcel.getId(), found.getId(), "L'ID du colis devrait correspondre");
	}

	@Test
	@DisplayName("Échouer la récupération d'un colis avec un ID nul")
	void testFindParcelById_NullId() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			service.findParcelById(null);
		});
		assertEquals("Parcel ID cannot be null", exception.getMessage());
	}

	@Test
	@DisplayName("Échouer la récupération d'un colis inexistant")
	void testFindParcelById_NotFound() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			service.findParcelById(999L);
		});
		assertEquals("Parcel with ID 999 not found", exception.getMessage());
	}

	@Test
	@DisplayName("Récupérer toutes les colis d'une réception")
	void testFindAllParcels() {
		Reception reception = createTestReception();
		Parcel parcel1 = new Parcel();
		parcel1.setReception(reception);
		parcel1.setWidth(150.0);
		parcel1.setLength(250.0);
		parcel1.setWeight(15.0);
		parcel1.setDeliveryDate(LocalDate.now().plusDays(2));
		parcel1.setCreatedAt(new Date());
		parcel1.setUpdatedAt(new Date());
		
		Parcel parcel2 = new Parcel();
		parcel2.setReception(reception);
		parcel2.setWidth(200.0);
		parcel2.setLength(300.0);
		parcel2.setWeight(20.0);
		parcel2.setDeliveryDate(LocalDate.now().plusDays(3));
		parcel2.setCreatedAt(new Date());
		parcel2.setUpdatedAt(new Date());
		

		em.getTransaction().begin();
		service.addParcel(parcel1);
		service.addParcel(parcel2);
		em.getTransaction().commit();

		assertEquals(3, service.findAllParcel().size(), "La réception devrait contenir deux colis");
	}
}
