package com.lattes.backend.api.controller;

import com.lattes.backend.api.dto.LoginDTO;
import com.lattes.backend.domain.model.Usuario;
import com.lattes.backend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UsuarioService service;

    public AuthController(UsuarioService service) {
        this.service = service;
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
}