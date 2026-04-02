package com.lattes.backend.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "instituicoes_openalex")
public class InstituicaoOpenAlex {

    @Id
    private String id;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "works_count")
    private Integer worksCount;
}