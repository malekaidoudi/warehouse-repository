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
        if (exception.getMessage().contains("category") && exception.getMessage().contains("must be between")) {
            errorCode = ErrorCodes.CATEGORY_INVALID_LABEL;
        } else if (exception.getMessage().contains("category") && exception.getMessage().contains("already exists")) {
             errorCode = ErrorCodes.CATEGORY_ALREADY_EXISTS;
        }else if (exception.getMessage().contains("dimension") && exception.getMessage().contains("already exists")) {
			errorCode = ErrorCodes.DIMENSION_ALREADY_EXISTS;
		} else if (exception.getMessage().contains("dimension") && exception.getMessage().contains("must be between")) {
			errorCode = ErrorCodes.DIMENSION_INVALID_LABEL;
		}else if (exception.getMessage().contains("dimension") && exception.getMessage().contains("must be positive")) {
			errorCode = ErrorCodes.DIMENSION_INVALID_DIMENSION;
		}else if(exception.getMessage().contains("parcel") && exception.getMessage().contains("associated with")) {
			errorCode = ErrorCodes.PARCEL_MISSING_RECEPTION_ID;
		}else if (exception.getMessage().contains("dates are mandatory")) {
			errorCode = ErrorCodes.DATE_DEL_EXP_MUST_BE_LINKED;
		} else if (exception.getMessage().contains("must be on or after the current date")) {
			errorCode = ErrorCodes.DATE_DEL_EXP_MUST_BE_VALID;
		} else if (exception.getMessage().contains("The expiration date must be after the delivery date")) {
				errorCode = ErrorCodes.DATE_DEL_EXP_MUST_BE_CONST;
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