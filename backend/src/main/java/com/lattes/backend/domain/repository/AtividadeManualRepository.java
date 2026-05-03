package com.lattes.backend.domain.repository;

import com.lattes.backend.domain.model.AtividadeManual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeManualRepository extends JpaRepository<AtividadeManual, Long> {
    
    // List não tem perigo de retornar null, então é mais seguro do que Optional para coleções.
    @Query("SELECT a FROM AtividadeManual a WHERE a.usuarioId = :usuarioId ORDER BY a.ano DESC")
    List<AtividadeManual> findByUsuarioIdOrderByAnoDesc(@Param("usuarioId") Long usuarioId);
}