package com.lattes.backend.api.dto;
import lombok.Builder;

@Builder
public record DemografiaDTO(
    String categoria, 
    Long totalAbsoluto, 
    Double porcentagem
) {}