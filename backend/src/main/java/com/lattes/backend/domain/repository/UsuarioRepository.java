package com.lattes.backend.domain.repository;

import com.lattes.backend.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Optional é uma classe do Java que pode conter um valor ou ser vazia, evitando o uso de null e prevenindo NullPointerExceptions.
    Optional<Usuario> findByEmail(String email);
}