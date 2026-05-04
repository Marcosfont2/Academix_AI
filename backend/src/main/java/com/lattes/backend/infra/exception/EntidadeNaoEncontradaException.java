package com.lattes.backend.infra.exception;

// Estender RuntimeException é regra no Spring Boot moderno, 
// pois não obriga você a colocar "throws" em todos os métodos.
// Erro de quando o service não acha o dado ou é nulo.
public class EntidadeNaoEncontradaException extends RuntimeException {
    
    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}