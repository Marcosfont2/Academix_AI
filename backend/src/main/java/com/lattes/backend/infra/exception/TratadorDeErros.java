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

    // 1. Intercepta erros quando não achamos algo no banco (Erro 404)
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErroPadraoDTO> tratarErro404(EntidadeNaoEncontradaException ex, HttpServletRequest request) {
        ErroPadraoDTO erro = new ErroPadraoDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // 2. Intercepta erros de regra de negócio (Erro 400)
    // Tipo tentar criar um curso com nome vazio, cadastrar um email já existente, etc
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroPadraoDTO> tratarRegraDeNegocio(RegraNegocioException ex, HttpServletRequest request) {
        ErroPadraoDTO erro = new ErroPadraoDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // 3. Intercepta parâmetros inválidos do frontend (Erro 400)
    // Por exemplo, o frontend manda um ID de curso como "abc" quando deveria ser um número.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroPadraoDTO> tratarRequisicaoInvalida(IllegalArgumentException ex, HttpServletRequest request) {
        ErroPadraoDTO erro = new ErroPadraoDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // 4. Intercepta falhas da IA (Erro 503)
    // Erro de comunicação com a API da IA, ou a IA retorna um erro.
    @ExceptionHandler(IntegracaoIAException.class)
    public ResponseEntity<ErroPadraoDTO> tratarIntegracaoIA(IntegracaoIAException ex, HttpServletRequest request) {
        ErroPadraoDTO erro = new ErroPadraoDTO(
                LocalDateTime.now(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Service Unavailable - IA",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(erro);
    }

    // 5. Intercepta falhas do Banco de Dados (Erro 503)
    // Pode ser o banco fora do ar, ou uma consulta que falhou por algum motivo.
    // Se o seu banco de dados PostgreSQL desligar do nada, ou se uma tabela for deletada por acidente
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErroPadraoDTO> tratarErroBancoDeDados(DataAccessException ex, HttpServletRequest request) {
        ErroPadraoDTO erro = new ErroPadraoDTO(
                LocalDateTime.now(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Database Error",
                "O banco de dados está temporariamente indisponível ou a consulta falhou.",
                request.getRequestURI()
        );
        ex.printStackTrace(); // Imprime no console para você debugar
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(erro);
    }

    // 6. O "Pega-Tudo" para qualquer erro não mapeado (Erro 500)
    // Se acontecer um erro que não foi previsto pelos outros handlers, ele cai aqui. Exemplo: um NullPointerException, ou um erro de lógica no código.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadraoDTO> tratarErro500(Exception ex, HttpServletRequest request) {
        ErroPadraoDTO erro = new ErroPadraoDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Ocorreu um erro interno inesperado no servidor.", 
                request.getRequestURI()
        );
        ex.printStackTrace(); 
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}