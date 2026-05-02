package com.lattes.backend.domain.repository; // package declaration

import com.lattes.backend.domain.model.ArtigoOpenAlex; // Importa a classe de modelo ArtigoOpenAlex
import org.springframework.data.jpa.repository.JpaRepository; // Spring Data JPA para operações de banco de dados
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository é a camada de acesso a dados, onde definimos as interfaces para interagir com o banco de dados.
// Não é sql puro, mas sim JPQL (Java Persistence Query Language), que é uma linguagem de consulta orientada a objetos.
// Daí o Hibernate traduz isso para SQL específico do banco de dados que estamos usando e manda a requisição para o banco 
// e recebemos a resposta. O Spring Data JPA cuida de toda essa tradução e execução para nós, então podemos focar em escrever consultas de alto nível.
@Repository
// Usa string para ID por preferência do OpenAlex, que tem IDs alfanuméricos
public interface ArtigoOpenAlexRepository extends JpaRepository<ArtigoOpenAlex, String> {

    // Retorna o tipo e a quantidade de artigos para cada tipo, ordenado pela quantidade em ordem decrescente
    @Query("SELECT a.tipo, COUNT(a) FROM ArtigoOpenAlex a WHERE a.tipo IS NOT NULL GROUP BY a.tipo ORDER BY COUNT(a) DESC")
    List<Object[]> contarPublicacoesPorTipo(); // Retorna uma lista de arrays, onde cada array tem o tipo e a contagem correspondente
}