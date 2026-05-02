package com.lattes.backend.domain.repository;

import com.lattes.backend.domain.model.AtividadeManual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeManualRepository extends JpaRepository<AtividadeManual, Long> {
    
    // List não tem perigo de retornar null, então é mais seguro do que Optional para coleções.
    List<AtividadeManual> findByUsuarioId(Long usuarioId);
}