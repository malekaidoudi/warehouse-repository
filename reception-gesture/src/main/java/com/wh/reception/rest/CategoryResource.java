package com.wh.reception.rest;

import com.wh.reception.domain.Category;
import com.wh.reception.services.ServiceCategories;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @Inject
    private ServiceCategories serviceCategories;

    @POST
    public Response createCategory(Category category) {
        try {
            category.validate(); // Appelle la méthode de validation
            serviceCategories.addCategory(category);
            return Response.status(Response.Status.CREATED)
                           .entity(category)
                           .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(e.getMessage())
                           .build();
        }
    }

    @GET
    public List<Category> getAllCategories() {
        return serviceCategories.findAllCategories();
    }

    @PUT
    @Path("/{id}")
    public Response updateCategory(@PathParam("id") Long id, Category category) {
        try {
            category.setId(id); // Assure que l'ID correspond à celui de l'URL
            category.validate();
            serviceCategories.updateCategory(category);
            return Response.ok(category).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(e.getMessage())
                           .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") Long id) {
        serviceCategories.deleteCategory(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response getCategoryById(@PathParam("id") Long id) {
        Category category = serviceCategories.findAllCategories()
                                            .stream()
                                            .filter(c -> c.getId().equals(id))
                                            .findFirst()
                                            .orElse(null);
        if (category == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Category not found")
                           .build();
        }
        return Response.ok(category).build();
    }
}