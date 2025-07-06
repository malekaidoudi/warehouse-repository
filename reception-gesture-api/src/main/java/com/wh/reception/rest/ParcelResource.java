package com.wh.reception.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wh.reception.domain.Parcel;
import com.wh.reception.rest.errors.ApiError;
import com.wh.reception.services.ServiceParcel;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/parcels")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParcelResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParcelResource.class);
	private static final String MODULE_NAME = "reception-gesture"; // Assurez-vous que ce nom correspond à votre module
																	// EJB
	private static final String ejbName = "ServiceParcelImp"; // Assurez-vous que ce nom correspond à votre module
																	// EJB
	private ServiceParcel serviceParcel;

	public ParcelResource() {
		try {
			this.serviceParcel = (ServiceParcel) Locator.lookup(MODULE_NAME, ejbName, ServiceParcel.class);
			LOGGER.info("ServiceCategories initialized successfully");
		} catch (Exception e) {
			LOGGER.error("Failed to initialize ServiceCategories", e);
			throw new RuntimeException("Failed to lookup ServiceCategories EJB", e);
		}
	}

	/**
	 * Endpoint to create a new parcel.
	 * 
	 * @param parcel the parcel to be created
	 * @return Response indicating the result of the operation
	 */

	@POST
	@Path("/")
	public Response createParcel(@Valid Parcel parcel) {
			serviceParcel.addParcel(parcel);
			LOGGER.info("Parcel created successfully: {}", parcel.getId());
			return Response.status(Response.Status.CREATED).entity(parcel).build();
	}

	/**
	 * Endpoint to update an existing parcel.
	 * 
	 * @param id     the ID of the parcel to be updated
	 * @param parcel the updated parcel data
	 * @return Response indicating the result of the operation
	 */
	@PUT
	@Path("/{id}")
	public Response updateParcel(@PathParam("id") Long id, @Valid Parcel parcel) {
		try {
			parcel.setId(id);
			serviceParcel.updateParcel(id,parcel);
			return Response.status(Response.Status.OK).entity(parcel).build();
		} catch (IllegalArgumentException e) {
			ApiError error = new ApiError("Parcel_002", "Invalid parcel data provided.", e.getMessage());
			LOGGER.error("Invalid parcel data provided: {}", e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
		}
	}

	/**
	 * Endpoint to delete a parcel by its ID.
	 * 
	 * @param id the ID of the parcel to be deleted
	 * @return Response indicating the result of the operation
	 */

	@DELETE
	@Path("/{id}")
	public Response deleteParcel(@PathParam("id") Long id) {
		try {
			serviceParcel.deleteParcel(id);
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (IllegalArgumentException e) {
			ApiError error = new ApiError("Parcel_003", "Parcel not found or invalid ID.", e.getMessage());
			LOGGER.error("Parcel not found or invalid ID: {}", e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
		}
	}

	/**
	 * 
	 * @param id
	 * @return Response containing the parcel with the specified ID
	 */

	@GET
	@Path("/{id}")
	public Response getParcelById(@PathParam("id") Long id) {
		try {
			Parcel parcel = serviceParcel.findParcelById(id);
			if (parcel == null) {
				ApiError error = new ApiError("Parcel_004", "Parcel not found.",
						"No parcel found with the provided ID.");
				LOGGER.error("Parcel not found: {}", id);
				return Response.status(Response.Status.NOT_FOUND).entity(error).build();
			}

			return Response.status(Response.Status.OK).entity(parcel).build();
		} catch (IllegalArgumentException e) {

			ApiError error = new ApiError("Parcel_005", "Invalid parcel ID provided.", e.getMessage());
			LOGGER.error("Invalid parcel ID provided: {}", e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
		}
	}

	/**
	 * Endpoint to retrieve all parcels associated with a specific reception ID.
	 * 
	 * @param receptionId the ID of the reception
	 * @return Response containing a list of parcels or an error message
	 */
	
	@GET
	@Path("/")
	public Response findAllParcelsByReceptionId(@QueryParam("receptionId") Long receptionId) {
		try {
			if (receptionId == null) {
				 ApiError error = new ApiError("Parcel_006", "Missing reception ID.", "Reception ID must be provided to retrieve parcels.");
		            LOGGER.error("Missing reception ID for findAllParcels request.");
		            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();

			} else {
				List<Parcel> parcels = serviceParcel.findParcelsByReceptionId(receptionId);
				
				return Response.ok(parcels).build();
			}
		} catch (Exception e) {
			ApiError error = new ApiError("Parcel_007", "An error occurred while retrieving parcels.", e.getMessage());
			LOGGER.error("Error retrieving parcels: {}", e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
		}
	}
	/**
	 * Endpoint to retrieve all parcels associated with a specific item ID.
	 * 
	 * @param itemId the ID of the item
	 * @return Response containing a list of parcels or an error message
	 */
	
	@GET
	@Path("/items")
	public Response findParcelsByItemId(@QueryParam("itemId") Long itemId) {
		try {
			if (itemId == null) {
				ApiError error = new ApiError("Parcel_008", "Missing item ID.", "Item ID must be provided to retrieve parcels.");
				LOGGER.error("Missing item ID for findParcelsByItemId request.");
				return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
			} else {
				List<Parcel> parcels = serviceParcel.findParcelsByItemId(itemId);
				return Response.ok(parcels).build();
			}
		} catch (Exception e) {
			ApiError error = new ApiError("Parcel_009", "An error occurred while retrieving parcels by item ID.", e.getMessage());
			LOGGER.error("Error retrieving parcels by item ID: {}", e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
		}
	}
	/**
	 * Endpoint to retrieve all parcels.
	 * 
	 * @return Response containing a list of all parcels or an error message
	 */	
	@GET
	public Response getAllParcels() {
		try {
			List<Parcel> parcels = serviceParcel.findAllParcel();
			if (parcels.isEmpty()) {
				ApiError error = new ApiError("Parcel_010", "No parcels found.", "There are no parcels in the system.");
				LOGGER.info("No parcels found in the system.");
				return Response.status(Response.Status.NO_CONTENT).entity(error).build();
			}
			return Response.ok(parcels).build();
		} catch (Exception e) {
			ApiError error = new ApiError("Parcel_011", "An error occurred while retrieving all parcels.", e.getMessage());
			LOGGER.error("Error retrieving all parcels: {}", e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
		}
	}

}
