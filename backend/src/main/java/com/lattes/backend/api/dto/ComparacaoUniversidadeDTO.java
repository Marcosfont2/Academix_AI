package com.lattes.backend.api.dto;

import java.util.List;
import lombok.Builder;

@Builder
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
    List<ItemContagemDTO> distribuicaoFaixaEtaria,       
    List<ItemContagemDTO> topAreasAvaliacao,             
    List<ItemContagemDTO> topGrandesAreasConhecimento,   
    List<ItemContagemDTO> distribuicaoConceitoPrograma,  
    
    // ==========================================
    // 3. LATTES PAINEL (Trajetória e Demografia)
    // ==========================================
    Long totalFormados,
    List<ItemContagemDTO> topPaisesNascimento,           
    List<ItemContagemDTO> topInstituicoesAtuacao,        
    List<ItemContagemDTO> topPaisesAtuacao,              
    
    // ==========================================
    // 4. DEMOGRAFIA DETALHADA
    // ==========================================
    List<DemografiaDTO> distribuicaoSexo,
    List<DemografiaDTO> distribuicaoRaca
) {}