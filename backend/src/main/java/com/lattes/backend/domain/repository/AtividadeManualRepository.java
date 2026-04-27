package com.lattes.backend.domain.repository;

import com.lattes.backend.domain.model.AtividadeManual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeManualRepository extends JpaRepository<AtividadeManual, Long> {
    
    // Método mágico do Spring: ele traduz isso para "SELECT * FROM atividades_manuais WHERE usuario_id = ?"
    List<AtividadeManual> findByUsuarioId(Long usuarioId);
}