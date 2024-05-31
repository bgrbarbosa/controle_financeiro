package br.com.controlefinanceiro.services.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final Long serialVersionUID = 1L;
    public ResourceNotFoundException(String entityNotFound) {
    }
}
