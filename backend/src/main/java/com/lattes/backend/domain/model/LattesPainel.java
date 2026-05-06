package com.lattes.backend.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "lattes_painel")
public class LattesPainel {

    @Id
    private Long id;

    @Column(name = "instituicao_formacao")
    private String instituicaoFormacao;

    @Column(name = "pais_nascimento")
    private String paisNascimento;
    
    @Column(name = "regiao_formacao")
    private String regiaoFormacao;

    @Column(name = "instituicao_atuacao")
    private String instituicaoAtuacao;

    @Column(name = "setor_atividade_atuacao")
    private String setorAtividadeAtuacao;

    @Column(name = "enquadramento_atuacao")
    private String enquadramentoAtuacao;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "cor_raca")
    private String corRaca;

    @Column(name = "contagem_registro")
    private Long contagemRegistro;

    @Column(name = "pais_atuacao")
    private String paisAtuacao;
}