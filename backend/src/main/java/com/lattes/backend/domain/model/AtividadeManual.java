package com.lattes.backend.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "atividades_manuais")
public class AtividadeManual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A chave estrangeira que liga essa atividade ao usuário dono dela
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    private Integer ano;
    
    private String tipo; // Ex: "Curso", "Congresso", "Projeto de Pesquisa"
    
    private String titulo;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;

    // Construtor vazio exigido pelo JPA/Hibernate
    public AtividadeManual() {
    }

    public AtividadeManual(Long usuarioId, Integer ano, String tipo, String titulo, String descricao) {
        this.usuarioId = usuarioId;
        this.ano = ano;
        this.tipo = tipo;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}