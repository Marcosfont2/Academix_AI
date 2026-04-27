package com.lattes.backend.api.dto;

public record EventoTimelineDTO(
    Integer ano,
    String tipo,      // Ex: "Formação", "Publicação", "Projeto"
    String titulo,    // Ex: "Graduação em Ciência da Computação"
    String descricao  // Ex: "Universidade Federal do Rio Grande do Norte"
) {}