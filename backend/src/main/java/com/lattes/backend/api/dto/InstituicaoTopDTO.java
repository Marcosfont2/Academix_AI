package com.lattes.backend.api.dto;
import lombok.Builder;

@Builder
public record InstituicaoTopDTO(String nome, Integer publicacoes) {
}