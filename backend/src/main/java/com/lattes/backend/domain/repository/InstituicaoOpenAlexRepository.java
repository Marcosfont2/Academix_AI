package com.lattes.backend.domain.repository;

import com.lattes.backend.domain.model.InstituicaoOpenAlex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstituicaoOpenAlexRepository extends JpaRepository<InstituicaoOpenAlex, String> {
    
    // O Spring traduz esse nome longo diretamente para a query SQL correta!
    List<InstituicaoOpenAlex> findTop10ByOrderByWorksCountDesc();
}