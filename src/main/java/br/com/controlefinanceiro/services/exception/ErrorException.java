package br.com.controlefinanceiro.services.exception;

public class ErrorException extends RuntimeException {

    private static final Long serialVersionUID = 1L;
    public ErrorException(String entityNotFound) {
    }
}
