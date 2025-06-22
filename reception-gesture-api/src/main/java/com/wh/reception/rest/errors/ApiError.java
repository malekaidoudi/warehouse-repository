package com.wh.reception.rest.errors; // Créez un nouveau package pour vos erreurs

import java.io.Serializable;

public class ApiError implements Serializable {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String message;
    private String details; // Optionnel, pour des détails supplémentaires
   
    public ApiError() {
    }

    public ApiError(String errorCode, String message, String details) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }

    // Getters et Setters
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

    @Override
    public String toString() {
        return "ApiError{" +
               "errorCode='" + errorCode + '\'' +
               ", message='" + message + '\'' +
               '}';
    }

}