package com.lattes.backend.service;

import com.lattes.backend.api.dto.AtividadeManualDTO;
import com.lattes.backend.api.dto.EventoTimelineDTO;
import com.lattes.backend.domain.model.AtividadeManual;
import com.lattes.backend.domain.repository.AtividadeManualRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoadmapService {

    private final AtividadeManualRepository atividadeManualRepository;

    public RoadmapService(AtividadeManualRepository atividadeManualRepository) {
        this.atividadeManualRepository = atividadeManualRepository;
    }

    // Método que salva o que vem do formulário do React
    public void salvarAtividadeManual(Long userId, AtividadeManualDTO dto) {
        AtividadeManual novaAtividade = AtividadeManual.builder()
            .usuarioId(userId)
            .ano(dto.ano())
            .tipo(dto.tipo())
            .titulo(dto.titulo())
            .descricao(dto.descricao())
            .build();
        atividadeManualRepository.save(novaAtividade);
    }

    // Método que constrói a Linha do Tempo
    public List<EventoTimelineDTO> gerarLinhaDoTempo(Long userId) {
        
        // 1. Busca direto do banco de dados (E já vem ordenado do maior pro menor!)
        List<AtividadeManual> atividadesExtras = atividadeManualRepository.findByUsuarioIdOrderByAnoDesc(userId);
        
        // 2. Transforma cada Atividade em um EventoTimelineDTO usando o Builder
        return atividadesExtras.stream()
            .map(extra -> EventoTimelineDTO.builder()
                .ano(extra.getAno())
                .tipo(extra.getTipo())
                .titulo(extra.getTitulo())
                .descricao(extra.getDescricao())
                .build()
            )
            .toList(); 
    }
}