package com.lattes.backend.api.dto;
import lombok.Builder;

@Builder
public record AtividadeManualDTO(
    Integer ano,
    String tipo,
    String titulo,
    String descricao
) {}