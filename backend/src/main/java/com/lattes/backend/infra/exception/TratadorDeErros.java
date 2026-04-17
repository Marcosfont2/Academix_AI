package com.lattes.backend.infra.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class TratadorDeErros {

    // 1. Intercepta erros quando não achamos algo no banco (Nosso Erro 404)
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErroPadraoDTO> tratarErro404(EntidadeNaoEncontradaException ex, HttpServletRequest request) {
        
        ErroPadraoDTO erro = new ErroPadraoDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(), // Código 404
                "Not Found",
                ex.getMessage(), // Pega a mensagem que mandamos lá do Service
                request.getRequestURI() // Pega a URL que o Frontend tentou acessar
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // 2. Um "Pega-Tudo" para qualquer erro genérico no código (Erro 500)
    // Isso evita que o Spring cuspa aquele stack trace gigante no Frontend
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadraoDTO> tratarErro500(Exception ex, HttpServletRequest request) {
        
        ErroPadraoDTO erro = new ErroPadraoDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // Código 500
                "Internal Server Error",
                "Ocorreu um erro interno inesperado no servidor.", // Escondemos o erro real por segurança
                request.getRequestURI()
        );
        
        // Imprime no console do Java para você (desenvolvedor) poder arrumar, mas não manda pro usuário
        ex.printStackTrace(); 
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    // 3. Tratador para falhas de conexão com o Banco de Dados ou Coluna inexistente
    // O DataAccessException é a classe mãe de todos os erros de banco no Spring (JPA/Hibernate)
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErroPadraoDTO> tratarErroBancoDeDados(DataAccessException ex, HttpServletRequest request) {
        ErroPadraoDTO erro = new ErroPadraoDTO(
                LocalDateTime.now(),
                HttpStatus.SERVICE_UNAVAILABLE.value(), // Código 503
                "Service Unavailable",
                "O banco de dados está temporariamente indisponível ou a consulta falhou.",
                request.getRequestURI()
        );
        
        ex.printStackTrace(); // Imprime no console para você debugar
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(erro);
    }

    // 4. Tratador para quando o usuário/front-end manda um parâmetro errado
    // Ex: Pediu o gráfico da área "Alienígenas", mas essa área não existe no sistema.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroPadraoDTO> tratarRequisicaoInvalida(IllegalArgumentException ex, HttpServletRequest request) {
        ErroPadraoDTO erro = new ErroPadraoDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), // Código 400
                "Bad Request",
                ex.getMessage(), // Exibe a mensagem do porquê foi inválido
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

}