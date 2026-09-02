package com.votaciones.api.exception;

// Recurso no encontrado -> 404 (ver GlobalExceptionHandler).
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
