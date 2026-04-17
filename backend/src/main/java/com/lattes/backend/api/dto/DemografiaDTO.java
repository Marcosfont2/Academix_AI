package com.lattes.backend.api.dto;

public record DemografiaDTO(
    String categoria, 
    Long totalAbsoluto, 
    Double porcentagem
) {}