package com.lattes.backend.api.controller;

import com.lattes.backend.api.dto.PublicacaoPorTipoDTO;
import com.lattes.backend.service.ArtigoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/artigos")
@CrossOrigin(origins = "http://localhost:3000")
public class ArtigoController {

    private final ArtigoService service;

    public ArtigoController(ArtigoService service) {
        this.service = service;
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<PublicacaoPorTipoDTO>> getTipos() {
        return ResponseEntity.ok(service.getDistribuicaoPorTipo());
    }
}