package com.lattes.backend.service;

import com.lattes.backend.api.dto.AreaConhecimentoDTO;
import com.lattes.backend.domain.repository.CapesDocenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CapesDocenteService {

    private final CapesDocenteRepository repository;

    public CapesDocenteService(CapesDocenteRepository repository) {
        this.repository = repository;
    }

    public List<AreaConhecimentoDTO> getTopAreasConhecimento() {
        List<Object[]> resultadosBanco = repository.contarDocentesPorArea();

        return resultadosBanco.stream()
                .limit(8) // Limitamos às Top 8 para o gráfico de Radar não virar uma bagunça
                .map(linha -> {
                    String area = (String) linha[0];
                    Long quantidade = ((Number) linha[1]).longValue();
                    return new AreaConhecimentoDTO(area, quantidade);
                })
                .collect(Collectors.toList());
    }
}