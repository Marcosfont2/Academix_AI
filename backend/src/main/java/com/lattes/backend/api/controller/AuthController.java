package com.lattes.backend.api.controller;

import com.lattes.backend.api.dto.AtividadeManualDTO;
import com.lattes.backend.api.dto.EventoTimelineDTO;
import com.lattes.backend.api.dto.LoginDTO;
import com.lattes.backend.domain.model.Usuario;
import com.lattes.backend.service.UsuarioService;
import com.lattes.backend.service.RoadmapService; // 1. Import do novo Service
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List; // 2. Import da Lista do Java

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UsuarioService service;
    private final RoadmapService roadmapService; // 3. Declarando a nova dependência

    // 4. Injetando no construtor
    public AuthController(UsuarioService service, RoadmapService roadmapService) {
        this.service = service;
        this.roadmapService = roadmapService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {
        return service.autenticar(login.email(), login.senha())
                .map(u -> ResponseEntity.ok(u))
                .orElse(ResponseEntity.status(401).build());
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario novoUsuario) {
        try {
            Usuario salvo = service.registrar(novoUsuario);
            return ResponseEntity.ok(salvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/upload-curriculo")
    public ResponseEntity<String> upload(@PathVariable Long id, @RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        try {
            service.salvarCurriculoXml(id, file);
            return ResponseEntity.ok("Currículo atualizado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao processar arquivo.");
        }
    }

    @GetMapping("/{id}/curriculo")
    public ResponseEntity<String> getCurriculo(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarCurriculo(id));
    }

    @GetMapping("/{id}/roadmap")
    public ResponseEntity<List<EventoTimelineDTO>> getRoadmap(@PathVariable Long id) {
        return ResponseEntity.ok(roadmapService.gerarLinhaDoTempo(id));
    }

    @PostMapping("/{id}/atividades")
    public ResponseEntity<String> adicionarAtividade(
            @PathVariable Long id, 
            @RequestBody AtividadeManualDTO dto) {
        try {
            roadmapService.salvarAtividadeManual(id, dto);
            return ResponseEntity.ok("Atividade adicionada com sucesso ao teu roadmap!");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao salvar atividade.");
        }
    }
}