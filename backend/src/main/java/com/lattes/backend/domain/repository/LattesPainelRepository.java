package com.lattes.backend.domain.repository;

import com.lattes.backend.domain.model.LattesPainel;
import com.lattes.backend.api.dto.ItemContagemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LattesPainelRepository extends JpaRepository<LattesPainel, Long> {
    
    // --- MÉTODOS ANTIGOS (Mantidos para o UC de Gráficos Básicos) ---
    @Query("SELECT l.sexo, SUM(l.contagemRegistro) FROM LattesPainel l WHERE l.sexo IS NOT NULL GROUP BY l.sexo")
    List<Object[]> contarRegistrosPorSexo();

    // --- MÉTODOS NOVOS (Para o UC de Comparação) ---
    
    // Note que mudamos ILIKE %:nome% para ILIKE :nome
    @Query("SELECT SUM(l.contagemRegistro) FROM LattesPainel l WHERE l.instituicaoFormacao ILIKE :nome")
    Long contarTotalFormadosDaUniversidade(@Param("nome") String nome);

    @Query("SELECT new com.lattes.backend.api.dto.ItemContagemDTO(l.paisNascimento, SUM(l.contagemRegistro)) " +
           "FROM LattesPainel l " +
           "WHERE l.instituicaoFormacao ILIKE :nome " +
           "AND l.paisNascimento IS NOT NULL AND l.paisNascimento <> 'Não Informado' " +
           "GROUP BY l.paisNascimento " +
           "ORDER BY SUM(l.contagemRegistro) DESC")
    List<ItemContagemDTO> contarTopPaisesNascimentoDaUniversidade(@Param("nome") String nome);

    @Query("SELECT new com.lattes.backend.api.dto.ItemContagemDTO(l.sexo, SUM(l.contagemRegistro)) " +
           "FROM LattesPainel l " +
           "WHERE l.instituicaoFormacao ILIKE :nome AND l.sexo IS NOT NULL " +
           "GROUP BY l.sexo")
    List<ItemContagemDTO> contarDistribuicaoSexoDaUniversidade(@Param("nome") String nome);

    @Query("SELECT new com.lattes.backend.api.dto.ItemContagemDTO(l.corRaca, SUM(l.contagemRegistro)) " +
           "FROM LattesPainel l " +
           "WHERE l.instituicaoFormacao ILIKE :nome AND l.corRaca IS NOT NULL AND l.corRaca <> 'Não Informado' " +
           "GROUP BY l.corRaca " +
           "ORDER BY SUM(l.contagemRegistro) DESC")
    List<ItemContagemDTO> contarDistribuicaoRacaDaUniversidade(@Param("nome") String nome);
    
    // Atualizado para os dois parâmetros
    @Query("SELECT SUM(l.contagemRegistro) FROM LattesPainel l " +
           "WHERE l.instituicaoFormacao ILIKE :nome AND l.instituicaoAtuacao ILIKE :nome")
    Long contarRetidosNaMesmaInstituicao(@Param("nome") String nome);

    // MÉTODOS DE BUSCA DE SUGESTÃO
    // Aqui mantemos o CONCAT nativo porque a string de pesquisa vem "limpa" do frontend (ex: "Fed")
    @Query(value = "SELECT DISTINCT instituicao_formacao FROM lattes_painel " +
                   "WHERE instituicao_formacao ILIKE CONCAT('%', :termo, '%') " +
                   "AND instituicao_formacao IS NOT NULL " +
                   "LIMIT 10", nativeQuery = true)
    List<String> buscarSugestoesDeNomes(@Param("termo") String termo);
}