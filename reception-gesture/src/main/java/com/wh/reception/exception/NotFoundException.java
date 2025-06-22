//import jakarta.ws.rs.ext.Provider; // Pas nécessaire ici, mais souvent utilisé avec ExceptionMapper
package com.wh.reception.exception;

public class NotFoundException extends RuntimeException {
    
		private static final long serialVersionUID = 1L;
	

    public NotFoundException(String message) {
        super(message);
    }
}