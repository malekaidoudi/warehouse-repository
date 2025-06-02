package com.wh.reception.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wh.reception.domain.Dimension;
import com.wh.reception.exception.NotFoundException;
import com.wh.reception.rest.errors.ApiError; // Assurez-vous que cette importation est correcte
import com.wh.reception.services.ServiceDimension;
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

@Path("/dimensions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DimensionResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DimensionResource.class);
    private static final String MODULE_NAME = "reception-gesture"; // Assurez-vous que ce nom correspond à votre module EJB
    private static final String ejbName = "ServiceDimensionImp"; // Assurez-vous que ce nom correspond à votre module EJB
	private ServiceDimension serviceDimension;

    public DimensionResource() {
        try {
            this.serviceDimension =(ServiceDimension) Locator.lookup(MODULE_NAME,ejbName, ServiceDimension.class);
            LOGGER.info("ServiceDimension initialized successfully");
        } catch (Exception e) {
            LOGGER.error("Failed to initialize ServiceDimension", e);
            throw new RuntimeException("Failed to lookup ServiceDimension EJB", e);
        }
    }

    @POST
    public Response createDimension(@Valid Dimension dimension) {
        try {
        	serviceDimension.addDimension(dimension);
            return Response.status(Response.Status.CREATED)
                           .entity(dimension)
                           .build();
        } catch (IllegalArgumentException e) {
            ApiError error = new ApiError("DIMENSION_001", "Invalid dimension data provided.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(error)
                           .build();
        }
    }

    @GET
    public Response getAllDimensions() {
    	System.out.println("Service dimension: " + serviceDimension);
        List<Dimension> dimension = serviceDimension.findAllDimensions();
        if (dimension.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(dimension).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateDimension(@PathParam("id") Long id, @Valid Dimension dimension) {
        try {
            dimension.setId(id);
            serviceDimension.updateDimension(dimension);
            return Response.ok(dimension).build();
        } catch (IllegalArgumentException e) {
            ApiError error = new ApiError("DIMENSION_002", "Invalid dimension data for update.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(error)
                           .build();
        } catch (NotFoundException e) {
            ApiError error = new ApiError("DIMENSION_003", "Dimension to update not found.", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(error)
                           .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDimension(@PathParam("id") Long id) {
        try {
        	serviceDimension.deleteDimension(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            ApiError error = new ApiError("DIMENSION_004", "Invalid dimension ID provided for deletion.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(error)
                           .build();
        } catch (NotFoundException e) {
            ApiError error = new ApiError("DIMENSION_005", "Dimension to delete not found.", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(error)
                           .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getDimensionById(@PathParam("id") Long id) {
        try {
            Dimension dimension = serviceDimension.findDimensionById(id);
            return Response.ok(dimension).build();
        } catch (IllegalArgumentException e) {
            ApiError error = new ApiError("DIMENSION_006", "Invalid dimension ID provided.", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(error)
                           .build();
        } catch (NotFoundException e) {
            ApiError error = new ApiError("DIMENSION_007", "Requested dimension not found.", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(error)
                           .build();
        }
    }
}