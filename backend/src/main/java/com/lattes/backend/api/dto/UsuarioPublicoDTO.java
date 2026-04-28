package com.lattes.backend.api.dto;

public record UsuarioPublicoDTO(
    Long id,
    String nome,
    String descricaoCurta
) {}