package com.wh.reception.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wh.reception.domain.Category;
import com.wh.reception.services.ServiceCategories;
import com.wh.reception.utilities.Locator;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryResource.class);
	private static final String MODULE_NAME = "reception-gesture"; // Assurez-vous que ce nom correspond à votre module
																	// EJB
	private static final String ejbName = "ServiceCategoriesImp"; // Assurez-vous que ce nom correspond à votre module
																	// EJB
	private ServiceCategories serviceCategories;

	public CategoryResource() {
		try {
			this.serviceCategories = (ServiceCategories) Locator.lookup(MODULE_NAME, ejbName, ServiceCategories.class);
			LOGGER.info("ServiceCategories initialized successfully");
		} catch (Exception e) {
			LOGGER.error("Failed to initialize ServiceCategories", e);
			throw new RuntimeException("Failed to lookup ServiceCategories EJB", e);
		}
	}

	/**
	 * Endpoint to create a new category.
	 * 
	 * @param category the category to be created
	 * @return Response indicating the result of the operation
	 */

	@POST
	public Response createCategory(Category category) {
		serviceCategories.addCategory(category);
		LOGGER.info("Category created successfully: {}", category.getLabel());
		return Response.status(Response.Status.CREATED).entity(category).build();
	}

	/**
	 * Endpoint to retrieve all categories.
	 * 
	 * @return Response containing the list of categories or a no content status
	 */

	@GET
	public Response getAllCategories() {

		LOGGER.info("Fetching all categories");
		List<Category> categories = serviceCategories.findAllCategories();
		if (categories.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		return Response.ok(categories).build();
	}

	/**
	 * Endpoint to update an existing category by its ID.
	 * 
	 * @param id the ID of the category to be updated
	 * @return Response indicating the result of the operation
	 */

	@PUT
	@Path("/{id}")
	public Response updateCategory(@PathParam("id") Long id, Category category) {
		category.setId(id);
		Category categoryUpdated = serviceCategories.updateCategory(id, category);
		LOGGER.info("Category updated successfully: {}", category.getLabel());
		return Response.ok(categoryUpdated).build();
	}

	/**
	 * Endpoint to delete a category by its ID.
	 * 
	 * @param id the ID of the category to be deleted
	 * @return Response indicating the result of the operation
	 */

	@DELETE
	@Path("/{id}")
	public Response deleteCategory(@PathParam("id") Long id) {
		serviceCategories.deleteCategory(id);
		LOGGER.info("Category with ID {} deleted successfully", id);
		return Response.noContent().build();
	}

	/**
	 * Endpoint to retrieve a category by its ID.
	 * 
	 * @param id
	 * @return Response containing the category with the specified ID
	 */

	@GET
	@Path("/{id}")
	public Response getCategoryById(@PathParam("id") Long id) {
		LOGGER.info("Fetching category with ID: {}", id);
		Category category = serviceCategories.findCategoryById(id);
		return Response.ok(category).build();
	}
}
