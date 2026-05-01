package com.lattes.backend.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder // Permite usar o padrão de projeto Builder para criar objetos de forma mais legível e flexível
@NoArgsConstructor // Exigência do JPA/Hibernate para ter um construtor vazio.
@AllArgsConstructor // Exigência do Lombok para gerar um construtor com todos os campos, necessário para o Builder.
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatiza a criação do ID
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(name = "curriculo_texto", columnDefinition = "TEXT")
    private String curriculoTexto;
}