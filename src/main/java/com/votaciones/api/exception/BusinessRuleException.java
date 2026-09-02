package com.votaciones.api.exception;

// Regla de negocio violada -> 409 (ver GlobalExceptionHandler).
public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String message) {
        super(message);
    }
}
