package com.lattes.backend.api.controller;

import com.lattes.backend.service.ConselheiroIAService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ia")
@CrossOrigin(origins = "http://localhost:3000")
public class ConselheiroIAController {

    private final ConselheiroIAService service;

    public ConselheiroIAController(ConselheiroIAService service) {
        this.service = service;
    }

    @GetMapping("/conselho/{userId}")
    public ResponseEntity<Map<String, String>> pedirConselho(@PathVariable Long userId) {
        String conselho = service.gerarConselhoCarreira(userId);
        // Retornamos um JSON simples em formato de mapa
        return ResponseEntity.ok(Map.of("resposta", conselho));
    }
}