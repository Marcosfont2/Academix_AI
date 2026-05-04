package com.lattes.backend.infra.exception;

import java.time.LocalDateTime;

// Usamos record para ser rápido e limpo.
public record ErroPadraoDTO(
        LocalDateTime timestamp, // Que horas o erro aconteceu
        Integer status,          // O código HTTP (ex: 404, 400)
        String erro,             // O nome do erro (ex: "Not Found")
        String mensagem,         // A mensagem amigável (ex: "Usuário ID 5 não existe")
        String caminho           // A URL que deu erro (ex: "/api/auth/5/curriculo")
) {}