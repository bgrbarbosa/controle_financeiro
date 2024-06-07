package br.com.controlefinanceiro.services.exception;

public class handleIntegrityConstraintViolationException extends RuntimeException {
    public handleIntegrityConstraintViolationException(String msg) {
        super(msg);
    }
}

