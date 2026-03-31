package com.lattes.backend.domain.repository;

import com.lattes.backend.domain.model.LattesPainel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LattesPainelRepository extends JpaRepository<LattesPainel, Long> {
    
    // Fazemos a soma (SUM) dos registros agrupados por sexo
    @Query("SELECT l.sexo, SUM(l.contagemRegistro) FROM LattesPainel l WHERE l.sexo IS NOT NULL GROUP BY l.sexo")
    List<Object[]> contarRegistrosPorSexo();
}