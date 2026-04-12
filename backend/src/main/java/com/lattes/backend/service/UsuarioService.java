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
}