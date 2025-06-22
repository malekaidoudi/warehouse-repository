package com.wh.reception.rest.errors;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericExceptionMapper.class);

    @Override
    public Response toResponse(Throwable exception) {
        LOGGER.error("Une erreur inattendue est survenue: {}", exception.getMessage(), exception);

        ApiError error = new ApiError(
            ErrorCodes.INTERNAL_SERVER_ERROR,
            "Une erreur interne du serveur est survenue.",
            "Veuillez réessayer plus tard ou contacter l'administrateur. Référence : " + System.currentTimeMillis()
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR) // 500
                       .entity(error)
                       .type(MediaType.APPLICATION_JSON)
                       .build();
    }
}