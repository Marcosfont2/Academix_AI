package com.lattes.backend.api.dto;
import lombok.Builder;

@Builder
public record AreaConhecimentoDTO(String area, Long quantidade) {
}