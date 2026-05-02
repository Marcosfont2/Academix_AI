package com.lattes.backend.domain.repository;

import com.lattes.backend.domain.model.InstituicaoOpenAlex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstituicaoOpenAlexRepository extends JpaRepository<InstituicaoOpenAlex, String> {
    
    List<InstituicaoOpenAlex> findTop10ByOrderByWorksCountDesc();

    // Onde é usado?
    // Aqui é a caixa de pesquisa do frontend com autocomplete.
    // Insensitive like e CONCAT são usados para permitir buscas parciais e case-insensitive, o que é ideal para uma funcionalidade de sugestão.
    @Query(value = "SELECT DISTINCT display_name FROM instituicoes_openalex " +
               "WHERE display_name ILIKE CONCAT('%', :termo, '%') " +
               "LIMIT 10", nativeQuery = true) // Native query significa que estamos usando SQL puro, então CONCAT é necessário para a busca com curinga
    List<String> buscarSugestoesLimpas(@Param("termo") String termo);

    // Onde é usado?
    // Param é para evitar SQL injection.
    // Mudamos de Optional para List para evitar o erro de múltiplos resultados, já que a busca por nome pode retornar várias instituições .
    @Query("SELECT o FROM InstituicaoOpenAlex o WHERE o.displayName ILIKE %:nome% ORDER BY o.worksCount DESC")
    List<InstituicaoOpenAlex> buscarPorNome(@Param("nome") String nome);
}