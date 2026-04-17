package com.lattes.backend.infra.exception;

// Estender RuntimeException é regra no Spring Boot moderno, 
// pois não obriga você a colocar "throws" em todos os métodos.
public class EntidadeNaoEncontradaException extends RuntimeException {
    
    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}