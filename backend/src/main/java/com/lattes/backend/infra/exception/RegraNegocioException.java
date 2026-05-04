package com.lattes.backend.infra.exception;

public class RegraNegocioException extends RuntimeException {
    
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}