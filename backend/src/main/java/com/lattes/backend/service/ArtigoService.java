package com.lattes.backend.service;

import com.lattes.backend.api.dto.PublicacaoPorTipoDTO;
import com.lattes.backend.domain.repository.ArtigoOpenAlexRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtigoService {

    private final ArtigoOpenAlexRepository repository;

    public ArtigoService(ArtigoOpenAlexRepository repository) {
        this.repository = repository;
    }

    // Recebemos a lista de objetos do banco de dados, transformamos cada um em um DTO e retornamos a lista de DTOs
    // Foi uma escolha deliberada continuar com lista de objetos ao invés de trazer uma lista de DTOs direto do banco,
    // Pois a lógica de negócio/transformação fica no service.
    public List<PublicacaoPorTipoDTO> getDistribuicaoPorTipo() {
        List<Object[]> resultadosBanco = repository.contarPublicacoesPorTipo();

        return resultadosBanco.stream()
                .map(linha -> {
                    String tipo = (String) linha[0];
                    Long quantidade = ((Number) linha[1]).longValue();
                    return PublicacaoPorTipoDTO.builder().tipo(tipo).quantidade(quantidade).build();
                })
                .toList(); // Coleta os DTOs em uma lista e retorna
    }

    // Caso trouxéssemos uma lista de DTOs direto do banco, o código seria algo como:
    /**
    public List<PublicacaoPorTipoDTO> getDistribuicaoPorTipo() {
        return repository.contarPublicacoesPorTipo();
    }
     */
}