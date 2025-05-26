package com.wh.reception.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
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
class ServicePaletteTest {
	
	private static Server h2WebServer;
	private ServiceReception serviceReception;
	private ServicePalette service;
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
		service = new ServicePaletteImp();
		((ServicePaletteImp) service).setEntityManager(em);
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
		// Pause le serveur H2 pour permettre à l'utilisateur de consulter la base de données
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
		Reception savedReception = serviceReception.createReception(reception);
		em.getTransaction().commit();

		 //pauseServer();

		return savedReception;
	}
	private Palette addTestPalette(Reception reception) {
		Palette palette = new Palette();
		palette.setReception(reception);
		palette.setHeight(100.0);
		palette.setWeight(50.0);
		palette.setDeliveryDate(LocalDate.now().plusDays(2));
		palette.setCreatedAt(new Date());
		palette.setUpdatedAt(new Date());

		em.getTransaction().begin();
		service.addPalette(palette);
		em.getTransaction().commit();

		return palette;
	}

	@Test
	@DisplayName("Ajouter une palette")
	void testAddPalette() {
		Reception reception = createTestReception();
		Palette palette = new Palette();
		palette.setReception(reception);
		palette.setHeight(200.0);
		palette.setWeight(50.0);
		palette.setDeliveryDate(LocalDate.now().plusDays(2));
		palette.setCreatedAt(new Date());
		palette.setUpdatedAt(new Date());
		em.getTransaction().begin();
		service.addPalette(palette);
		em.getTransaction().commit();
		// pauseServer();
		assertNotNull(palette.getId(), "L'ID de la palette devrait être généré");
		assertEquals(2, serviceReception.getPalettesCount(reception.getId()), "La réception devrait contenir une palette");
	}

	@Test
	@DisplayName("Mettre à jour une palette")
	void testUpdatePalette() {
		Reception reception = createTestReception();
		Palette palette = new Palette();
		palette.setReception(reception);
		palette.setHeight(100.0);
		palette.setWeight(50.0);
		palette.setDeliveryDate(LocalDate.now().plusDays(2));
		palette.setCreatedAt(new Date());
		palette.setUpdatedAt(new Date());
		em.getTransaction().begin();
		service.addPalette(palette);
		em.getTransaction().commit();
		palette.setWeight(60.0);
		em.getTransaction().begin();
		service.updatePalette(palette);
		em.getTransaction().commit();
		Palette updated = em.find(Palette.class, palette.getId());
		assertEquals(60.0, updated.getWeight(), 0.01, "Le poids de la palette devrait être mis à jour");
	}

	@Test
	@DisplayName("Supprimer une palette")
	void testDeletePalette() {
		Reception reception = createTestReception();
		Palette palette = new Palette();
		palette.setReception(reception);
		palette.setHeight(100.0);
		palette.setWeight(50.0);
		palette.setDeliveryDate(LocalDate.now().plusDays(2));
		palette.setCreatedAt(new Date());
		palette.setUpdatedAt(new Date());
		em.getTransaction().begin();
		service.addPalette(palette);
		em.getTransaction().commit();
		em.getTransaction().begin();
		service.deletePalette(palette.getId());
		em.getTransaction().commit();
		Palette deleted = em.find(Palette.class, palette.getId());
		assertNull(deleted, "La palette devrait être supprimée");
		assertEquals(1, serviceReception.getPalettesCount(reception.getId()),
				"La réception ne devrait plus contenir de palettes");
	}
	
	@Test
	@DisplayName("Récupérer toutes les palettes d'une réception")
	void testFindAllPalettes_Success() {
		Reception reception = createTestReception();
		Palette palette1 = addTestPalette(reception);
		Palette palette2 = addTestPalette(reception);

		List<Palette> palettes = service.findAllPalettes(reception.getId());

		assertNotNull(palettes, "La liste des palettes ne devrait pas être nulle");
		assertEquals(3, palettes.size(), "La réception devrait contenir trois palettes");
		assertTrue(palettes.stream().anyMatch(p -> p.getId().equals(palette1.getId())),
				"La première palette devrait être dans la liste");
		assertTrue(palettes.stream().anyMatch(p -> p.getId().equals(palette2.getId())),
				"La seconde palette devrait être dans la liste");
	}
	
	@Test
	@DisplayName("Récupérer aucune palette pour une réception sans palettes")
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
		serviceReception.createReception(reception);
		em.getTransaction().commit();

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
	@DisplayName("Échouer la récupération d'une palette inexistante")
	void testFindPaletteById_NotFound() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			service.findPaletteById(999L);
		});
		assertEquals("Palette with ID 999 not found", exception.getMessage());
	}


}
