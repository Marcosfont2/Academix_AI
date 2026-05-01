package com.lattes.backend.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atividades_manuais")
public class AtividadeManual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatiza a criação do ID
    private Long id;

    // A chave estrangeira que liga essa atividade ao usuário dono dela
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    // Sem Column pois o nome do campo já é "ano", que é o mesmo nome da coluna na tabela, então o JPA mapeia automaticamente
    private Integer ano;
    
    private String tipo; // Ex: "Curso", "Congresso", "Projeto de Pesquisa"
    
    private String titulo;
    
    @Column(columnDefinition = "TEXT") // Permite armazenar descrições mais longas
    private String descricao;
}