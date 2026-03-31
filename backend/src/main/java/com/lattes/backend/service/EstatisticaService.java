package com.lattes.backend.service;

import com.lattes.backend.api.dto.GeneroPorcentagemDTO;
import com.lattes.backend.domain.repository.LattesPainelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstatisticaService {

    private final LattesPainelRepository repository;

    public EstatisticaService(LattesPainelRepository repository) {
        this.repository = repository;
    }

    public List<GeneroPorcentagemDTO> calcularPorcentagemPorGenero() {
        List<Object[]> resultadosBanco = repository.contarRegistrosPorSexo();
        
        // 1. Descobrir o total geral para calcular a porcentagem
        double totalGeral = 0;
        for (Object[] linha : resultadosBanco) {
            totalGeral += ((Number) linha[1]).doubleValue();
        }

        // 2. Montar a lista de DTOs com a porcentagem calculada
        List<GeneroPorcentagemDTO> porcentagens = new ArrayList<>();
        for (Object[] linha : resultadosBanco) {
            String genero = (String) linha[0];
            double quantidade = ((Number) linha[1]).doubleValue();
            
            double porcentagem = (quantidade / totalGeral) * 100;
            // Arredondando para 2 casas decimais
            porcentagem = Math.round(porcentagem * 100.0) / 100.0; 
            
            porcentagens.add(new GeneroPorcentagemDTO(genero, porcentagem));
        }

        return porcentagens;
    }
}