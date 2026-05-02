package com.lattes.backend.service;

import com.lattes.backend.api.dto.InstituicaoTopDTO;
import com.lattes.backend.domain.model.InstituicaoOpenAlex;
import com.lattes.backend.domain.repository.InstituicaoOpenAlexRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstituicaoService {

    private final InstituicaoOpenAlexRepository repository;

    public InstituicaoService(InstituicaoOpenAlexRepository repository) {
        this.repository = repository;
    }

    public List<InstituicaoTopDTO> getTop10Instituicoes() {
        List<InstituicaoOpenAlex> topInstituicoes = repository.findTop10ByOrderByWorksCountDesc();

        // Converte a Entidade pesada do banco para o DTO leve
        return topInstituicoes.stream()
                .map(inst -> InstituicaoTopDTO.builder().nome(inst.getDisplayName()).publicacoes(inst.getWorksCount()).build())
                .collect(Collectors.toList());
    }
}