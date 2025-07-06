package com.wh.reception.rest.errors;

import com.wh.reception.exception.NotFoundException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotFoundExceptionMapper.class);

	@Override
	public Response toResponse(NotFoundException exception) {
		String errorCode = ErrorCodes.NOT_FOUND_GENERIC; // Code d'erreur générique pour les ressources non trouvées
		if (exception.getMessage().contains("not found")) {
			if (exception.getMessage().contains("Category")) {
				errorCode = ErrorCodes.CATEGORY_NOT_FOUND;
			} else if (exception.getMessage().contains("Dimension")) {
				errorCode = ErrorCodes.DIMENSION_NOT_FOUND;
			} else if (exception.getMessage().contains("Parcel")) {
				errorCode = ErrorCodes.PARCEL_NOT_FOUND;
			} else if (exception.getMessage().contains("Reception")) {
				//errorCode = ErrorCodes.RECEPTION_NOT_FOUND;
			} else if (exception.getMessage().contains("Item")) {
				//errorCode = ErrorCodes.ITEM_NOT_FOUND;
			} else if (exception.getMessage().contains("Palette")) {
				//errorCode = ErrorCodes.PALETTE_NOT_FOUND;
			} 
		}
		// Créer un objet ApiError avec le code d'erreur et le message
		ApiError error = new ApiError(errorCode, "Resource not found.", exception.getMessage());
		LOGGER.warn("NotFoundException: [{}] {} - Details: {}", errorCode, "Resource not found.",
				exception.getMessage());
		// Retourner une réponse 404 avec l'objet ApiError
		return Response.status(Response.Status.NOT_FOUND)
				.entity(error).type(MediaType.APPLICATION_JSON).build();
	}
}