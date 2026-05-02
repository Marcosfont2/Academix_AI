package com.lattes.backend.api.dto;
import lombok.Builder;

@Builder
public record GeneroPorcentagemDTO(String genero, Double porcentagem) {
}