package com.lattes.backend.service;

import com.lattes.backend.api.dto.ComparacaoUniversidadeDTO;
import com.lattes.backend.api.dto.DemografiaDTO;
import com.lattes.backend.api.dto.ItemContagemDTO;
import com.lattes.backend.domain.model.InstituicaoOpenAlex;
import com.lattes.backend.domain.repository.CapesDocenteRepository;
import com.lattes.backend.domain.repository.InstituicaoOpenAlexRepository;
import com.lattes.backend.domain.repository.LattesPainelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComparacaoUniversidadeService {

    private final InstituicaoOpenAlexRepository openAlexRepo;
    private final CapesDocenteRepository capesRepo;
    private final LattesPainelRepository lattesRepo;

    public ComparacaoUniversidadeService(
            InstituicaoOpenAlexRepository openAlexRepo,
            CapesDocenteRepository capesRepo,
            LattesPainelRepository lattesRepo) {
        this.openAlexRepo = openAlexRepo;
        this.capesRepo = capesRepo;
        this.lattesRepo = lattesRepo;
    }

    public ComparacaoUniversidadeDTO obterDadosComparacao(String nomeUniversidade) {
        
        // 1. OPENALEX (Agora lidando com uma lista)
        List<InstituicaoOpenAlex> resultados = openAlexRepo.buscarPorNome(nomeUniversidade);

        // Se a lista não estiver vazia, pegamos o primeiro item (índice 0)
        InstituicaoOpenAlex instituicao = resultados.isEmpty() ? null : resultados.get(0);

        Integer totalArtigos = (instituicao != null) ? instituicao.getWorksCount() : 0;
        Integer totalCitacoes = (instituicao != null) ? instituicao.getCitedByCount() : 0;

        // 2. CAPES DOCENTES
        Long totalDocentes = capesRepo.contarTotalDocentesDaUniversidade(nomeUniversidade);
        if (totalDocentes == null) totalDocentes = 0L; // Prevenção de nulos
        
        List<ItemContagemDTO> faixaEtaria = capesRepo.contarDistribuicaoFaixaEtariaDaUniversidade(nomeUniversidade);
        List<ItemContagemDTO> topAreas = capesRepo.contarTopAreasAvaliacaoDaUniversidade(nomeUniversidade);

        // 3. LATTES PAINEL
        Long totalFormados = lattesRepo.contarTotalFormadosDaUniversidade(nomeUniversidade);
        if (totalFormados == null) totalFormados = 0L;
        
        List<ItemContagemDTO> topPaises = lattesRepo.contarTopPaisesNascimentoDaUniversidade(nomeUniversidade);
        Long retidos = lattesRepo.contarRetidosNaMesmaInstituicao(nomeUniversidade);
        if (retidos == null) retidos = 0L;

        // --- CÁLCULO DE DEMOGRAFIA (Nativo do Java) ---
        List<ItemContagemDTO> sexoBruto = lattesRepo.contarDistribuicaoSexoDaUniversidade(nomeUniversidade);
        List<DemografiaDTO> distribuicaoSexo = calcularPorcentagemDemografia(sexoBruto);

        List<ItemContagemDTO> racaBruta = lattesRepo.contarDistribuicaoRacaDaUniversidade(nomeUniversidade);
        List<DemografiaDTO> distribuicaoRaca = calcularPorcentagemDemografia(racaBruta);

        // 4. MONTANDO O DTO FINAL
        // Obs: Preenchi com List.of() os métodos do repositório que nós ainda não criamos
        // para que seu código compile de primeira. Você pode adicioná-los depois!
        return new ComparacaoUniversidadeDTO(
                nomeUniversidade,
                
                totalArtigos, 
                totalCitacoes,
                
                totalDocentes, 
                faixaEtaria, 
                topAreas,
                List.of(), // topGrandesAreasConhecimento (A fazer)
                List.of(), // distribuicaoGrauPrograma (A fazer)
                List.of(), // distribuicaoConceitoPrograma (A fazer)
                
                totalFormados, 
                topPaises, 
                List.of(), // distribuicaoRegiaoFormacao (A fazer)
                
                List.of(), // topInstituicoesAtuacao (A fazer)
                List.of(), // distribuicaoSetorAtividade (A fazer)
                List.of(), // distribuicaoEnquadramento (A fazer)
                
                distribuicaoSexo, 
                distribuicaoRaca
        );
    }

    /**
     * Algoritmo nativo para processar a porcentagem matemática com base no total do grupo.
     */
    private List<DemografiaDTO> calcularPorcentagemDemografia(List<ItemContagemDTO> dadosBrutos) {
        if (dadosBrutos == null || dadosBrutos.isEmpty()) {
            return List.of();
        }

        // 1. Descobre o 100% (Soma de todos os registros daquela lista)
        long totalGeral = dadosBrutos.stream()
                .mapToLong(ItemContagemDTO::quantidade)
                .sum();

        if (totalGeral == 0) return List.of();

        // 2. Mapeia cada item transformando em DemografiaDTO com a porcentagem
        return dadosBrutos.stream().map(item -> {
            double porcentagemExata = (item.quantidade() * 100.0) / totalGeral;
            // Arredonda para 2 casas decimais (ex: 33.33)
            double porcentagemArredondada = Math.round(porcentagemExata * 100.0) / 100.0;
            
            return new DemografiaDTO(item.chave(), item.quantidade(), porcentagemArredondada);
        }).collect(Collectors.toList());
    }
}