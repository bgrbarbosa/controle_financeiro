package br.com.controlefinanceiro.services.exception;

public class EntityNotFoundException extends RuntimeException {

    private static final Long serialVersionUID = 1L;
    public EntityNotFoundException(String entityNotFound) {
    }
}
