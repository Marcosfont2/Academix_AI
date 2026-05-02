package com.lattes.backend.domain.model; // package declaration

/** 
 * O jakarta é o conjunto de especificações (regras e padrões) sobre como as coisas devem ser feitas no Java moderno.
 *
 * O JPA (java persistence API) é um subconjunto dentro jakarta, que dita como o java deve conversar com banco de dados usando
 * ORM (Mapeamento objeto-relacional).
 * Ele fornece uma maneira de mapear objetos Java (classes, entidades, etc) para tabelas de banco de dados e vice-versa,
 * facilitando a persistência de dados em aplicações Java ao remover a necessidade de escrever SQL manualmente para operações
 * comuns de banco de dados.
 *
 * O Hibernate é a biblioteca que executa o JPA, ou seja, é a implementação do JPA. 
 *
 * O Spring Boot é um framework completo para uma aplicação em java. Ele usa o Hibernate como provedor de JPA por padrão.
 * Se você fosse usar apenas o Hibernate e o JPA sozinhos, você teria que escrever dezenas de arquivos XML chatos,
 * configurar conexões de banco de dados na mão e gerenciar servidores.
 * O Spring Boot abstrai e prepara tudo isso, embute um servidor web (Tomcat) para receber requisições HTTP do next.js,
 * configura o Hibernate e o JPA para você, e traz o Spring Data JPA, que é uma biblioteca que facilita a criação de repositórios
 * (classes que fazem a ponte entre o banco de dados e o código Java).
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Usando Lombok para gerar getters, setters e outros métodos comuns automaticamente
import lombok.Data;

@Data // Anotação do Lombok para gerar getters, setters, equals, hashCode e toString
/**
 * No Java, nós criamos dezenas de classes diferentes: classes de segurança, classes para enviar email,
 * DTOs para o frontend, etc. Como o Spring Boot sabe qual classe deve ser salva no banco de dados e qual
 * é apenas lógica interna? Daí o @Entity entra
 * 
 * Sem o @Entity: A classe ArtigoOpenAlex seria apenas um POJO (Plain Old Java Object - um objeto Java comum).
 * Ela existiria apenas na memória RAM enquanto o código roda e desapareceria assim que o programa fosse desligado.
 * 
 * Com o @Entity: O Hibernate (que fica vigiando o seu código) vê que é entity e nota que a classe é especial.
 * Ela representa uma tabela real lá no PostgreSQL e é preciso monitorar os objetos dessa classe e garantir que,
 * se o desenvolvedor mudar o 'tipo' no código, que seja gerado um UPDATE no banco de dados automaticamente".
 */
@Entity // Anotação do JPA para indicar que esta classe é uma entidade persistente
@Table(name = "openalex_artigos") // Especifica o nome da tabela no banco de dados
public class ArtigoOpenAlex {

    @Id
    @Column(name = "id_openalex")
    private String idOpenAlex;

    @Column(name = "tipo")
    private String tipo;

    // Outras colunas são desnecessárias para o nosso projeto, então não precisamos mapear elas aqui.
    // Iria custar mais tempo e espaço de armazenamento, então é melhor deixar só o que é necessário.
}