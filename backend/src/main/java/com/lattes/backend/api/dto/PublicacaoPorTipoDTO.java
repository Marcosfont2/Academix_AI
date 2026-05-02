package com.lattes.backend.api.dto;
import lombok.Builder;

@Builder
public record PublicacaoPorTipoDTO(String tipo, Long quantidade) {
}