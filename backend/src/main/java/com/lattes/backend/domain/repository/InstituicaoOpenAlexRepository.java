package com.lattes.backend.domain.repository;

import com.lattes.backend.domain.model.InstituicaoOpenAlex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstituicaoOpenAlexRepository extends JpaRepository<InstituicaoOpenAlex, String> {
    
    List<InstituicaoOpenAlex> findTop10ByOrderByWorksCountDesc();

    // Mudamos de Optional para List para evitar o erro de múltiplos resultados
    @Query("SELECT o FROM InstituicaoOpenAlex o WHERE o.displayName ILIKE %:nome% ORDER BY o.worksCount DESC")
    List<InstituicaoOpenAlex> buscarPorNome(@Param("nome") String nome);
}