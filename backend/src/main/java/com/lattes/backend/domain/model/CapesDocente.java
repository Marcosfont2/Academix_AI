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

    @Column(name = "nm_area_conhecimento")
    private String nmAreaConhecimento;
}