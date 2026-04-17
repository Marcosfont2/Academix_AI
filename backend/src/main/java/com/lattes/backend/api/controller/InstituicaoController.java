package com.lattes.backend.api.controller;

import com.lattes.backend.api.dto.InstituicaoTopDTO;
import com.lattes.backend.service.InstituicaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/instituicoes")
@CrossOrigin(origins = "http://localhost:3000")
public class InstituicaoController {

    private final InstituicaoService service;

    public InstituicaoController(InstituicaoService service) {
        this.service = service;
    }

    @GetMapping("/top-publicacoes")
    public ResponseEntity<List<InstituicaoTopDTO>> getTopPublicacoes() {
        List<InstituicaoTopDTO> dados = service.getTop10Instituicoes();
        
        if (dados == null || dados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(dados);
    }
}