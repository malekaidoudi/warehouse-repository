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

import com.wh.reception.domain.Category;
import com.wh.reception.exception.NotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceCategoriesTest {

	private static Server h2WebServer;
	private ServiceCategories service;
	private EntityManager em;
	private EntityManagerFactory emf;

	@BeforeAll
	void startH2Console() throws SQLException {
//		if (Boolean.getBoolean("h2.console.enabled")) {
			h2WebServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
			System.out.println("Console H2 démarrée à : " + h2WebServer.getURL());
//		}
	}

	@AfterAll
	void stopH2Console() {
//		if (h2WebServer != null) {
			h2WebServer.stop();
			System.out.println("Console H2 arrêtée.");
		//}
	}

	@BeforeEach
	void setUp() {
		emf = Persistence.createEntityManagerFactory("UP_WAREHOUSE_TEST");
		em = emf.createEntityManager();
		service = new ServiceCategoriesImp();
		((ServiceCategoriesImp) service).setEntityManager(em);
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

	@Test
	@DisplayName("Ajouter une catégorie")
	void testAddCategory() {
		Category category = new Category();

		category.setLabel("TestCategory");
		category.setDescription("Test description");

		em.getTransaction().begin();
		service.addCategory(category);
		em.getTransaction().commit();

		pauseServer();
		assertNotNull(category.getId(), "L'ID de la categorie devrait être généré");
	}

	@Test
	@DisplayName("Mettre à jour une catégorie")
	void testUpdateCategory() {
		Category category = new Category();
		category.setLabel("TestCategory");
		category.setDescription("Test description");

		em.getTransaction().begin();
		service.addCategory(category);
		em.getTransaction().commit();
		
		category.setLabel("UpdatedLabelCategory");
		category.setDescription("UpdatedDescriptionCategory");
		
		em.getTransaction().begin();
		service.updateCategory(category.getId(),category);
		em.getTransaction().commit();

		assertEquals("UpdatedLabelCategory", category.getLabel(), "Le label de la catégorie devrait être mis à jour");
		assertEquals("UpdatedDescriptionCategory", category.getDescription(),
				"La description de la catégorie devrait être mis à jour");

		pauseServer();
		
		category.setId(category.getId()+1L);
		
		try {
			service.updateCategory(category.getId(), category);
			fail("NotFoundException expected");
		} catch (NotFoundException e) {
			assertEquals("Category with ID " + category.getId() + " not found for update.", e.getMessage());
		}
	}

	@Test
	@DisplayName("Supprimer une catégorie")
	void testDeleteCategory() {
		Category category = new Category();
		category.setLabel("TestCategory");
		category.setDescription("Test description");

		em.getTransaction().begin();
		service.addCategory(category);
		em.getTransaction().commit();
		
		assertNotNull(category.getId(), "L'ID de la categorie devrait être généré");
//pauseServer();
		em.getTransaction().begin();
		service.deleteCategory(category.getId());
		em.getTransaction().commit();
		
		List<Category> categories = service.findAllCategories();
		assertNotNull(categories, "La liste des catégories ne devrait pas être nulle");
		assertEquals(0, categories.size(), "La liste des catégories devrait être vide");
		
		
		try {
			service.deleteCategory(category.getId());
			fail("NotFoundException expected");
		} catch (NotFoundException e) {
			assertEquals("Category with ID " + category.getId() + " not found", e.getMessage());
		}
	}

	@Test
	@DisplayName("Récupérer toutes les catégories")
	void testFindAllCategories() {
		// Vérifie que la liste des catégories est vide au début
		List<Category> emptyCategories = service.findAllCategories();
		assertNotNull(emptyCategories, "La liste des catégories ne devrait pas être nulle");
		assertEquals(0, emptyCategories.size(), "La liste des catégories devrait être vide");
	
		
		Category category1 = new Category();
		category1.setLabel("TestCategory1");
		category1.setDescription("Test description 1");

		Category category2 = new Category();
		category2.setLabel("TestCategory2");
		category2.setDescription("Test description 2");

		em.getTransaction().begin();
		service.addCategory(category1);
		service.addCategory(category2);
		em.getTransaction().commit();

		List<Category> categories = service.findAllCategories();

		assertNotNull(categories, "La liste des catégories ne devrait pas être nulle");
		assertEquals(2, categories.size(), "Il devrait y avoir deux catégories");
		
	}

	@Test
	@DisplayName("Récupérer une catégorie par ID")
	void testFindCategoryById() {
		Category category = new Category();
		category.setLabel("TestCategory");
		category.setDescription("Test description");

		em.getTransaction().begin();
		service.addCategory(category);
		em.getTransaction().commit();

		Category foundCategory = service.findCategoryById(category.getId());

		assertNotNull(foundCategory, "La catégorie trouvée ne devrait pas être nulle");
		assertEquals(category.getId(), foundCategory.getId(), "L'ID de la catégorie trouvée devrait correspondre");
		assertEquals(category.getLabel(), foundCategory.getLabel(), "Le label de la catégorie trouvée devrait correspondre");
		assertEquals(category.getDescription(), foundCategory.getDescription(),
				"La description de la catégorie trouvée devrait correspondre");
		try {
			service.findCategoryById(category.getId()+1L);
			fail("NotFoundException expected");
		} catch (NotFoundException e) {
			assertEquals("Category with ID " + (category.getId()+1L) + " not found", e.getMessage());
		}
	}
}
