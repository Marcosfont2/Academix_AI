package com.lattes.backend.api.dto;

import java.util.List;

public record ComparacaoUniversidadeDTO(
    String nomeUniversidade,
    
    // ==========================================
    // 1. OPENALEX (Impacto Global)
    // ==========================================
    Integer totalArtigos,
    Integer totalCitacoes,
    
    // ==========================================
    // 2. CAPES DOCENTES (Estrutura Acadêmica)
    // ==========================================
    Long totalDocentes,
    List<ItemContagemDTO> distribuicaoFaixaEtaria,       // Ex: [{"chave": "40 A 44 ANOS", "quantidade": 150}, ...]
    List<ItemContagemDTO> topAreasAvaliacao,             // Ex: [{"chave": "FARMÁCIA", "quantidade": 45}]
    List<ItemContagemDTO> topGrandesAreasConhecimento,   // Ex: [{"chave": "CIÊNCIAS DA SAÚDE", "quantidade": 300}]
    List<ItemContagemDTO> distribuicaoGrauPrograma,      // Ex: [{"chave": "MESTRADO", "quantidade": 200}, {"chave": "DOUTORADO", "quantidade": 150}]
    List<ItemContagemDTO> distribuicaoConceitoPrograma,  // Ex: Notas 3, 4, 5, 6, 7
    
    // ==========================================
    // 3. LATTES PAINEL (Trajetória e Demografia)
    // ==========================================
    Long totalFormados,
    List<ItemContagemDTO> topPaisesNascimento,           // Top 5 países
    List<ItemContagemDTO> distribuicaoRegiaoFormacao,    // Norte, Nordeste, Sudeste, etc (incluindo "Não Informado")
    
    // Atuação (Para onde vão os egressos?)
    List<ItemContagemDTO> topInstituicoesAtuacao,        // Onde eles trabalham agora
    List<ItemContagemDTO> distribuicaoSetorAtividade,    // Setor Público, Privado, Exterior
    List<ItemContagemDTO> distribuicaoEnquadramento,     // Pós-doutorando, Professor Titular, etc
    
    // Demografia detalhada
    List<DemografiaDTO> distribuicaoSexo,
    List<DemografiaDTO> distribuicaoRaca
) {}