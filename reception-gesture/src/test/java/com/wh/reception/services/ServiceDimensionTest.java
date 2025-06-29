package com.wh.reception.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

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

import com.wh.reception.domain.Dimension;
import com.wh.reception.exception.InvalidDataException;
import com.wh.reception.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceDimensionTest {

	private static Server h2WebServer;
	private ServiceDimension service;
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
		service = new ServiceDimensionImp();
		((ServiceDimensionImp) service).setEntityManager(em);
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

	Dimension addTestDimension() {
		Dimension dimension = new Dimension();
		dimension.setLabel("TestDimension");
		dimension.setWidth(100d);
		dimension.setLength(200d);

		em.getTransaction().begin();
		service.addDimension(dimension);
		em.getTransaction().commit();
		return dimension;
	}

	@Test
	@DisplayName("Ajouter une dimension")
	void testAddDimension() {

		Dimension dimension = addTestDimension();
		dimension.setLabel(null);
		dimension.setWidth(100d);
		dimension.setLength(200d);

		try {
			service.addDimension(dimension);
			fail("InvalidDataException expected");
		} catch (InvalidDataException e) {
			assertEquals("The dimension label cannot be null or empty.", e.getMessage());
		}
		// pauseServer();
		dimension.setLabel("");
		dimension.setWidth(100d);
		dimension.setLength(200d);

		try {
			service.addDimension(dimension);
			fail("InvalidDataException expected");
		} catch (InvalidDataException e) {
			assertEquals("The dimension label cannot be null or empty.", e.getMessage());
		}
		// pauseServer();
		dimension.setLabel("ab");
		dimension.setWidth(100d);
		dimension.setLength(200d);

		try {
			service.addDimension(dimension);
			fail("InvalidDataException expected");
		} catch (InvalidDataException e) {
			assertEquals("The label must be between 3 and 50 characters.", e.getMessage());
		}
		// pauseServer();
		dimension.setLabel("a".repeat(51));
		dimension.setWidth(100d);
		dimension.setLength(200d);

		try {
			service.addDimension(dimension);
			fail("InvalidDataException expected");
		} catch (InvalidDataException e) {
			assertEquals("The label must be between 3 and 50 characters.", e.getMessage());
		}

		// pauseServer();
		dimension.setLabel("TestDimension");
		dimension.setWidth(-1d);
		dimension.setLength(200d);

		try {
			service.addDimension(dimension);
			fail("InvalidDataException expected");
		} catch (InvalidDataException e) {
			assertEquals("The dimension width must be positive and smaller than the length.", e.getMessage());
		}
		// pauseServer();
		dimension.setLabel("TestDimension");
		dimension.setWidth(100d);
		dimension.setLength(50d);
		try {
			service.addDimension(dimension);
			fail("InvalidDataException expected");
		} catch (InvalidDataException e) {
			assertEquals("The dimension width must be positive and smaller than the length.", e.getMessage());
		}
		// pauseServer();

		dimension.setLabel("TestDimension");
		dimension.setWidth(100d);
		dimension.setLength(200d);
		try {
			service.addDimension(dimension);
			fail("InvalidDataException expected");
		} catch (InvalidDataException e) {
			assertEquals("A dimension label " + dimension.getLabel() + " already exists.", e.getMessage());
		}
		Dimension dimension2 = new Dimension();
		dimension2.setLabel("DimensionTest");
		dimension2.setWidth(100d);
		dimension2.setLength(200d);
		em.getTransaction().begin();
		service.addDimension(dimension2);
		em.getTransaction().commit();
		assertNotNull(dimension2.getId(), "L'ID de la dimension devrait être généré");

	}

	@Test
	@DisplayName("Mettre à jour une dimension")
	void testUpdateDimension() {
		Dimension dimension = addTestDimension();

		dimension.setLabel("UpdatedLabelDimension");
		dimension.setWidth(101d);
		dimension.setLength(201d);
		em.getTransaction().begin();
		service.updateDimension(dimension.getId(), dimension);
		em.getTransaction().commit();

		assertEquals("UpdatedLabelDimension", dimension.getLabel(), "Le label de la dimension devrait être mis à jour");
		assertEquals(101, dimension.getWidth(), "La largeur de la dimension devrait être mise à jour");
		assertEquals(201, dimension.getLength(), "La longueur de la dimension devrait être mise à jour");

		// pauseServer();

		dimension.setId(dimension.getId() + 1L);

		try {
			service.updateDimension(dimension.getId(), dimension);
			fail("NotFoundException expected");
		} catch (NotFoundException e) {
			assertEquals("Dimension with ID " + dimension.getId() + " not found", e.getMessage());
		}
	}

	@Test
	@DisplayName("Supprimer une dimension")
	void testDeleteDimension() {
		Dimension dimension = addTestDimension();

		em.getTransaction().begin();
		service.deleteDimension(dimension.getId());
		em.getTransaction().commit();

		assertNull(em.find(Dimension.class, dimension.getId()), "La dimension devrait être supprimée");

		// pauseServer();

		try {
			service.deleteDimension(dimension.getId());
			fail("NotFoundException expected");
		} catch (NotFoundException e) {
			assertEquals("Dimension with ID " + dimension.getId() + " not found", e.getMessage());
		}
	}

	@Test
	@DisplayName("Récupérer toutes les dimensions")
	void testFindAllDimensions() {
		assertEquals(0, service.findAllDimensions().size(),
				"the dimensions list should be empty at the start");
		Dimension dimension1 = new Dimension();
		dimension1.setLabel("TestDimension1");
		dimension1.setWidth(100d);
		dimension1.setLength(200d);

		Dimension dimension2 = new Dimension();
		dimension2.setLabel("TestDimension2");
		dimension2.setWidth(150d);
		dimension2.setLength(250d);

		em.getTransaction().begin();
		service.addDimension(dimension1);
		service.addDimension(dimension2);
		em.getTransaction().commit();

		List<Dimension> dimensions = service.findAllDimensions();

		assertNotNull(dimensions, "the dimensions list should not be null");
		assertEquals(2, dimensions.size(), "should find 2 dimensions");
	}

	@Test
	@DisplayName("Récupérer une dimension par ID")
	void testFindDimensionById() {
		Dimension dimension = addTestDimension();

		Dimension foundDimension = service.findDimensionById(dimension.getId());

		assertNotNull(foundDimension, "the found dimension should not be null");
		assertEquals(dimension.getId(), foundDimension.getId(), "the ID of the found dimension should match");
		try {
			service.findDimensionById(dimension.getId() + 1L);
			fail("NotFoundException expected");
		} catch (NotFoundException e) {
			assertEquals("Dimension with ID " + (dimension.getId()+1L) + " not found", e.getMessage());
		}

		// pauseServer();

	}
}
