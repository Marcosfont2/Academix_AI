package com.lattes.backend.domain.repository; // package declaration

import com.lattes.backend.domain.model.ArtigoOpenAlex; // Importa a classe de modelo ArtigoOpenAlex
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtigoOpenAlexRepository extends JpaRepository<ArtigoOpenAlex, String> {

    @Query("SELECT a.tipo, COUNT(a) FROM ArtigoOpenAlex a WHERE a.tipo IS NOT NULL GROUP BY a.tipo ORDER BY COUNT(a) DESC")
    List<Object[]> contarPublicacoesPorTipo();
}