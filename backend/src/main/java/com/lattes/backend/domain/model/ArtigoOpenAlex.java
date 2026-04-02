package com.lattes.backend.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "openalex_artigos")
public class ArtigoOpenAlex {

    @Id
    @Column(name = "id_openalex")
    private String idOpenAlex;

    @Column(name = "tipo")
    private String tipo;
}