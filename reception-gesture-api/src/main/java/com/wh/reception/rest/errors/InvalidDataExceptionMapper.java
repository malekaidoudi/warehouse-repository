package com.wh.reception.rest.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wh.reception.exception.InvalidDataException; // IMPORTER DE VOTRE PROJET gesture-reception

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidDataExceptionMapper implements ExceptionMapper<InvalidDataException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidDataExceptionMapper.class);

    @Override
    public Response toResponse(InvalidDataException exception) {
        String errorCode;
        // Déduction du code d'erreur basée sur le message ou des conventions
        if (exception.getMessage().contains("label must be between") && exception.getMessage().contains("empty")) {
            errorCode = ErrorCodes.CATEGORY_INVALID_DATA;
        } else if (exception.getMessage().contains("category") && exception.getMessage().contains("already exists")) {
             errorCode = ErrorCodes.CATEGORY_ALREADY_EXISTS;
        } else if (exception.getMessage().contains("ID") && exception.getMessage().contains("invalide")) {
             errorCode = ErrorCodes.BAD_REQUEST_GENERIC; 
        }
        // ... Ajoutez d'autres conditions ...
        else {
            errorCode = ErrorCodes.BAD_REQUEST_GENERIC;
        }
       // "Données fournies invalides."
        ApiError error = new ApiError(errorCode,"Invalid data provided.", exception.getMessage());
        LOGGER.warn("InvalidDataException: [{}] {} - Details: {}", errorCode, "Invalid data provided.", exception.getMessage());

        return Response.status(Response.Status.BAD_REQUEST) // 400
                       .entity(error)
                       .type(MediaType.APPLICATION_JSON)
                       .build();
    }
}