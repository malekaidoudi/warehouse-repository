package com.wh.reception.rest;

import java.util.List;

import com.wh.reception.domain.Dimension;
import com.wh.reception.services.ServiceDimension;
import com.wh.utilities.Locator;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/dimension")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DimensionResource {

	ServiceDimension serviceDimension = (ServiceDimension) Locator.lookup("ServiceDimensionImp",
			ServiceDimension.class);

	@POST
	public Response createDimension(Dimension dimension) {

		try {
			dimension.validate();
			serviceDimension.addDimension(dimension);
			return Response.status(Response.Status.CREATED).entity(dimension).build();
		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@GET
	public List<Dimension> getAllDimensions() {
		return serviceDimension.findAllDimensions();
	}

	@POST
	public Response updateDimension(Dimension dimension) {
		try {
			dimension.validate();
			serviceDimension.updateDimension(dimension);
			return Response.ok(dimension).build();
		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response deleteDimension(@PathParam("id") Long id) {
		serviceDimension.deleteDimension(id);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id}")
	public Dimension findDimensionById(@PathParam("id") Long id) {
		return serviceDimension.findDimensionById(id);
	}
}
