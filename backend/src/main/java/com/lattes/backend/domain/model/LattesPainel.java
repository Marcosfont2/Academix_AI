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
    private Long id; // Agora é só o ID, sem a anotação de geração automática!

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "contagem_registro")
    private Long contagemRegistro;
}