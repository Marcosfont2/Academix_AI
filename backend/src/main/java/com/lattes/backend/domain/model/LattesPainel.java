package com.lattes.backend.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data // O Lombok já cria os Getters e Setters automaticamente para você!
@Entity
@Table(name = "lattes_painel")
public class LattesPainel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Toda tabela JPA precisa de um ID

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "contagem_registro")
    private Long contagemRegistro;
}