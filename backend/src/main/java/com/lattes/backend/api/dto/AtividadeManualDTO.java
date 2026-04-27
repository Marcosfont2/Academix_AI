package com.lattes.backend.api.dto;

public record AtividadeManualDTO(
    Integer ano,
    String tipo,
    String titulo,
    String descricao
) {}