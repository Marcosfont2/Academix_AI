package com.lattes.backend.api.dto;
import lombok.Builder;

@Builder
public record UsuarioPublicoDTO(
    Long id,
    String nome,
    String descricaoCurta
) {}