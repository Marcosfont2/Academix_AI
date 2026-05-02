package com.lattes.backend.api.dto;
import lombok.Builder;

@Builder
public record LoginDTO(String email, String senha) {}