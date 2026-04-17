package com.lattes.backend.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "capes_docentes")
public class CapesDocente {

    @Id
    @Column(name = "id_pessoa")
    private Long idPessoa;

    @Column(name = "nm_entidade_ensino")
    private String nmEntidadeEnsino;

    @Column(name = "nm_area_avaliacao")
    private String nmAreaAvaliacao;

    @Column(name = "nm_grande_area_conhecimento")
    private String nmGrandeAreaConhecimento;

    @Column(name = "nm_area_conhecimento")
    private String nmAreaConhecimento;

    @Column(name = "nm_grau_programa")
    private String nmGrauPrograma;

    @Column(name = "nm_modalidade_programa")
    private String nmModalidadePrograma;

    @Column(name = "cd_conceito_programa")
    private String cdConceitoPrograma;

    @Column(name = "ds_faixa_etaria")
    private String dsFaixaEtaria;
}