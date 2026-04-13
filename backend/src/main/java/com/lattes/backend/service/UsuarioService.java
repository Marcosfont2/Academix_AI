package com.lattes.backend.service;

import com.lattes.backend.domain.model.Usuario;
import com.lattes.backend.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Optional<Usuario> autenticar(String email, String senha) {
        return repository.findByEmail(email)
                .filter(u -> u.getSenha().equals(senha)); // Futuramente aplicar BCrypt aqui
    }

    public void atualizarCurriculo(Long userId, String novoTexto) {
        repository.findById(userId).ifPresent(u -> {
            u.setCurriculoTexto(novoTexto);
            repository.save(u);
        });
    }

    public Usuario registrar(Usuario novoUsuario) {
        // Regra de ouro: Não deixar criar dois usuários com o mesmo email
        if (repository.findByEmail(novoUsuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado!");
        }
        return repository.save(novoUsuario);
    }

    public void salvarCurriculoXml(Long userId, org.springframework.web.multipart.MultipartFile arquivo) throws Exception {
        // Lê todos os bytes do arquivo e transforma em String
        String conteudo = new String(arquivo.getBytes(), java.nio.charset.StandardCharsets.UTF_8);
        
        atualizarCurriculo(userId, conteudo);
    }

    public String buscarCurriculo(Long userId) {
        return repository.findById(userId)
            .map(Usuario::getCurriculoTexto)
            .orElse("Currículo não encontrado.");
    }
}