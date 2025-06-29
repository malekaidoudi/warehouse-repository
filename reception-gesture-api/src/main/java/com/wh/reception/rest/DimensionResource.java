package com.wh.reception.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wh.reception.domain.Dimension;
import com.wh.reception.services.ServiceDimension;
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

@Path("/dimensions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DimensionResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(DimensionResource.class);
	private static final String MODULE_NAME = "reception-gesture"; // Assurez-vous que ce nom correspond à votre module
																	// EJB
	private static final String ejbName = "ServiceDimensionImp"; // Assurez-vous que ce nom correspond à votre module
																	// EJB
	private ServiceDimension serviceDimension;

	public DimensionResource() {
		try {
			this.serviceDimension = (ServiceDimension) Locator.lookup(MODULE_NAME, ejbName, ServiceDimension.class);
			LOGGER.info("ServiceDimension initialized successfully");
		} catch (Exception e) {
			LOGGER.error("Failed to initialize ServiceDimension", e);
			throw new RuntimeException("Failed to lookup ServiceDimension EJB", e);
		}
	}

	/**
	 * Endpoint to create a new dimension.
	 *
	 * @param id        the ID of the dimension to be created
	 * @param dimension the dimension data to be created
	 * @return Response indicating the result of the operation
	 */

	@POST
	public Response createDimension(@PathParam("id") Long id,Dimension dimension) {
		serviceDimension.addDimension(dimension);
		LOGGER.info("Dimension created successfully: {}", dimension.getLabel());
		return Response.status(Response.Status.CREATED).entity(dimension).build();
	}

	/**
	 * Endpoint to retrieve all dimensions.
	 *
	 * @return Response containing the list of all dimensions
	 */

	@GET
	public Response getAllDimensions() {
		
		LOGGER.info("Fetching all dimensions");
		List<Dimension> dimension = serviceDimension.findAllDimensions();
		if (dimension.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		return Response.ok(dimension).build();
	}

	/**
	 * Endpoint to update an existing dimension.
	 *
	 * @param id        the ID of the dimension to be updated
	 * @param dimension the updated dimension data
	 * @return Response indicating the result of the operation
	 */

	@PUT
	@Path("/{id}")
	public Response updateDimension(@PathParam("id") Long id,Dimension dimension) {
		dimension.setId(id);
		Dimension updatedDimension = serviceDimension.updateDimension(id, dimension);
		LOGGER.info("Dimension updated successfully: {}", dimension.getLabel());
		return Response.ok(updatedDimension).build();
	}

	/**
	 * Endpoint to delete a dimension by its ID.
	 *
	 * @param id the ID of the dimension to be deleted
	 * @return Response indicating the result of the operation
	 */

	@DELETE
	@Path("/{id}")
	public Response deleteDimension(@PathParam("id") Long id) {
		serviceDimension.deleteDimension(id);
		LOGGER.info("Deleting dimension with ID: {}", id);
		return Response.noContent().build();
	}

	/**
	 * Endpoint to retrieve a dimension by its ID.
	 *
	 * @param id the ID of the dimension to be retrieved
	 * @return Response containing the requested dimension or an error if not found
	 */

	@GET
	@Path("/{id}")
	public Response getDimensionById(@PathParam("id") Long id) {
		Dimension dimension = serviceDimension.findDimensionById(id);
		LOGGER.info("Fetching dimension with ID: {}", id);
		return Response.ok(dimension).build();
	}
}