package com.lattes.backend.api.controller;

import com.lattes.backend.api.dto.AreaConhecimentoDTO;
import com.lattes.backend.service.CapesDocenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
@CrossOrigin(origins = "http://localhost:3000")
public class CapesDocenteController {

    private final CapesDocenteService service;

    public CapesDocenteController(CapesDocenteService service) {
        this.service = service;
    }

    @GetMapping("/areas")
    public ResponseEntity<List<AreaConhecimentoDTO>> getAreas() {
        List<AreaConhecimentoDTO> dados = service.getTopAreasConhecimento();
        
        if (dados == null || dados.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(dados);
    }
}