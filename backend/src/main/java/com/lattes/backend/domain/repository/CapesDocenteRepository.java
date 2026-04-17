package com.lattes.backend.domain.repository;

import com.lattes.backend.domain.model.CapesDocente;
import com.lattes.backend.api.dto.ItemContagemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapesDocenteRepository extends JpaRepository<CapesDocente, Long> {

    // --- MÉTODOS ANTIGOS (Mantidos para o UC de Gráficos Básicos) ---
    @Query("SELECT c.nmAreaConhecimento, COUNT(c) FROM CapesDocente c WHERE c.nmAreaConhecimento IS NOT NULL GROUP BY c.nmAreaConhecimento ORDER BY COUNT(c) DESC")
    List<Object[]> contarDocentesPorArea();

    // --- MÉTODOS NOVOS (Para o UC de Comparação) ---
    @Query("SELECT COUNT(c) FROM CapesDocente c WHERE c.nmEntidadeEnsino ILIKE %:nome%")
    Long contarTotalDocentesDaUniversidade(@Param("nome") String nome);

    @Query("SELECT new com.lattes.backend.api.dto.ItemContagemDTO(c.dsFaixaEtaria, COUNT(c)) " +
           "FROM CapesDocente c " +
           "WHERE c.nmEntidadeEnsino ILIKE %:nome% AND c.dsFaixaEtaria IS NOT NULL " +
           "GROUP BY c.dsFaixaEtaria " +
           "ORDER BY COUNT(c) DESC")
    List<ItemContagemDTO> contarDistribuicaoFaixaEtariaDaUniversidade(@Param("nome") String nome);

    @Query("SELECT new com.lattes.backend.api.dto.ItemContagemDTO(c.nmAreaAvaliacao, COUNT(c)) " +
           "FROM CapesDocente c " +
           "WHERE c.nmEntidadeEnsino ILIKE %:nome% AND c.nmAreaAvaliacao IS NOT NULL " +
           "GROUP BY c.nmAreaAvaliacao " +
           "ORDER BY COUNT(c) DESC")
    List<ItemContagemDTO> contarTopAreasAvaliacaoDaUniversidade(@Param("nome") String nome);
}