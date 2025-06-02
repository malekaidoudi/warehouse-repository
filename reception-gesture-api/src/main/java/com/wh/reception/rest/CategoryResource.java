package com.wh.reception.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wh.reception.domain.Category;
import com.wh.reception.exception.NotFoundException;
import com.wh.reception.rest.errors.ApiError; // Assurez-vous que cette importation est correcte
import com.wh.reception.services.ServiceCategories;
import com.wh.reception.utilities.Locator;

import jakarta.validation.Valid;
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DimensionResource.class);
    private static final String MODULE_NAME = "reception-gesture"; // Assurez-vous que ce nom correspond à votre module EJB
    private static final String ejbName = "ServiceCategoriesImp"; // Assurez-vous que ce nom correspond à votre module EJB
	private ServiceCategories serviceCategories;

    public CategoryResource() {
        try {
            this.serviceCategories =(ServiceCategories) Locator.lookup(MODULE_NAME,ejbName, ServiceCategories.class);
            LOGGER.info("ServiceCategories initialized successfully");
        } catch (Exception e) {
            LOGGER.error("Failed to initialize ServiceCategories", e);
            throw new RuntimeException("Failed to lookup ServiceCategories EJB", e);
        }
    }

    @POST
    public Response createCategory(@Valid Category category) {
        try {
            serviceCategories.addCategory(category);
            return Response.status(Response.Status.CREATED)
                           .entity(category)
                           .build();
        } catch (IllegalArgumentException e) {
            ApiError error = new ApiError("CATEGORY_001", "Invalid category data provided.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(error)
                           .build();
        }
    }

    @GET
    public Response getAllCategories() {
    	System.out.println("ServiceCategories: " + serviceCategories);
        List<Category> categories = serviceCategories.findAllCategories();
        if (categories.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(categories).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCategory(@PathParam("id") Long id, @Valid Category category) {
        try {
            category.setId(id);
            serviceCategories.updateCategory(category);
            return Response.ok(category).build();
        } catch (IllegalArgumentException e) {
            ApiError error = new ApiError("CATEGORY_002", "Invalid category data for update.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(error)
                           .build();
        } catch (NotFoundException e) {
            ApiError error = new ApiError("CATEGORY_003", "Category to update not found.", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(error)
                           .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") Long id) {
        try {
            serviceCategories.deleteCategory(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            ApiError error = new ApiError("CATEGORY_004", "Invalid category ID provided for deletion.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(error)
                           .build();
        } catch (NotFoundException e) {
            ApiError error = new ApiError("CATEGORY_005", "Category to delete not found.", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(error)
                           .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getCategoryById(@PathParam("id") Long id) {
        try {
            Category category = serviceCategories.findCategoryById(id);
            return Response.ok(category).build();
        } catch (IllegalArgumentException e) {
            ApiError error = new ApiError("CATEGORY_006", "Invalid category ID provided.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(error)
                           .build();
        } catch (NotFoundException e) {
            ApiError error = new ApiError("CATEGORY_007", "Requested category not found.", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(error)
                           .build();
        }
    }
}