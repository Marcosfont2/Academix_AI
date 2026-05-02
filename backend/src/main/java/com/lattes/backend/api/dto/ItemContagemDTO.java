package com.lattes.backend.api.dto;
import lombok.Builder;

@Builder
public record ItemContagemDTO(
    String chave, 
    Long quantidade
) {}