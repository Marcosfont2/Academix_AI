package com.lattes.backend.domain.repository;

import com.lattes.backend.domain.model.CapesDocente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapesDocenteRepository extends JpaRepository<CapesDocente, Long> {

    @Query("SELECT c.nmAreaConhecimento, COUNT(c) FROM CapesDocente c WHERE c.nmAreaConhecimento IS NOT NULL GROUP BY c.nmAreaConhecimento ORDER BY COUNT(c) DESC")
    List<Object[]> contarDocentesPorArea();
}