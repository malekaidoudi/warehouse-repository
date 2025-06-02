package com.wh.reception.rest;

import com.wh.reception.exception.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    // Maps NotFoundException to a JAX-RS Response
    //you can use MediaType.APPLICATION_JSON to specify the response content type
    public Response toResponse(NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                       .entity(exception.getMessage())
                       .type("text/plain")
                       .build();
    }
}
