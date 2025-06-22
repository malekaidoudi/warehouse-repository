package com.wh.reception.rest.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wh.reception.exception.InvalidDataException; // Importez depuis gesture-reception
import com.wh.reception.exception.NotFoundException; // Importez depuis gesture-reception

import jakarta.ejb.EJBException; // Importez la classe EJBException
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EJBExceptionMapper.class);

    @Override
    public Response toResponse(EJBException exception) {
        Throwable cause = exception.getCause(); // Récupérer la cause réelle de l'EJBException

        if (cause instanceof NotFoundException) {
            // Si la cause est une NotFoundException, déléguer à son mapper spécifique
            LOGGER.warn("Caught EJBException wrapping NotFoundException: {}", cause.getMessage());
            return new NotFoundExceptionMapper().toResponse((NotFoundException) cause);
        } else if (cause instanceof InvalidDataException) {
            // Si la cause est une InvalidDataException, déléguer à son mapper spécifique
            LOGGER.warn("Caught EJBException wrapping InvalidDataException: {}", cause.getMessage());
            return new InvalidDataExceptionMapper().toResponse((InvalidDataException) cause);
        } else {
            // Si la cause n'est pas une de nos exceptions métier connues,
            // ou si l'EJBException n'a pas de cause (ce qui est rare mais possible),
            // la traiter comme une erreur générique du serveur.
            LOGGER.error("Caught EJBException with unexpected cause: {}", cause != null ? cause.getMessage() : "No cause provided.", exception);
            // Vous pouvez aussi déléguer au GenericExceptionMapper pour la cohérence
            return new GenericExceptionMapper().toResponse(exception); // Passez l'EJBException ou sa cause originale si vous voulez
        }
    }
}