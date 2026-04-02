package com.lattes.backend.service;

import com.lattes.backend.api.dto.PublicacaoPorTipoDTO;
import com.lattes.backend.domain.repository.ArtigoOpenAlexRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtigoService {

    private final ArtigoOpenAlexRepository repository;

    public ArtigoService(ArtigoOpenAlexRepository repository) {
        this.repository = repository;
    }

    public List<PublicacaoPorTipoDTO> getDistribuicaoPorTipo() {
        List<Object[]> resultadosBanco = repository.contarPublicacoesPorTipo();

        return resultadosBanco.stream()
                .map(linha -> {
                    String tipo = (String) linha[0];
                    Long quantidade = ((Number) linha[1]).longValue();
                    return new PublicacaoPorTipoDTO(tipo, quantidade);
                })
                .collect(Collectors.toList());
    }
}