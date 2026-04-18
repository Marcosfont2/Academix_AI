package com.lattes.backend.service;

import com.lattes.backend.api.dto.ComparacaoUniversidadeDTO;
import com.lattes.backend.api.dto.DemografiaDTO;
import com.lattes.backend.api.dto.ItemContagemDTO;
import com.lattes.backend.domain.model.InstituicaoOpenAlex;
import com.lattes.backend.domain.repository.CapesDocenteRepository;
import com.lattes.backend.domain.repository.InstituicaoOpenAlexRepository;
import com.lattes.backend.domain.repository.LattesPainelRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

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
        
        // 1. OPENALEX (Usa o nome cru com % nas consultas dele)
        List<InstituicaoOpenAlex> openAlexOpt = openAlexRepo.buscarPorNome(nomeUniversidade);
        InstituicaoOpenAlex instituicao = openAlexOpt.isEmpty() ? null : openAlexOpt.get(0);
        Integer totalArtigos = (instituicao != null) ? instituicao.getWorksCount() : 0;
        Integer totalCitacoes = (instituicao != null) ? instituicao.getCitedByCount() : 0;

        // 2. CAPES DOCENTES (Também usa o nome cru pois mantivemos os % no repositório dele)
        Long totalDocentes = capesRepo.contarTotalDocentesDaUniversidade(nomeUniversidade);
        if (totalDocentes == null) totalDocentes = 0L; 
        
        List<ItemContagemDTO> faixaEtaria = capesRepo.contarDistribuicaoFaixaEtariaDaUniversidade(nomeUniversidade);
        List<ItemContagemDTO> topAreas = capesRepo.contarTopAreasAvaliacaoDaUniversidade(nomeUniversidade);


        // ==========================================
        // 3. LATTES PAINEL (AQUI ENTRA O PULO DO GATO!)
        // ==========================================
        
        // Passamos o nome na nossa máquina de lavar dados antes de mandar pro banco
        String nomeLattesProcessado = normalizarParaLattes(nomeUniversidade);

        // Agora usamos a variável 'nomeLattesProcessado' em todas as chamadas do Lattes
        Long totalFormados = lattesRepo.contarTotalFormadosDaUniversidade(nomeLattesProcessado);
        if (totalFormados == null) totalFormados = 0L;
        
        List<ItemContagemDTO> topPaises = lattesRepo.contarTopPaisesNascimentoDaUniversidade(nomeLattesProcessado);
        
        Long retidos = lattesRepo.contarRetidosNaMesmaInstituicao(nomeLattesProcessado);
        if (retidos == null) retidos = 0L;

        // --- CÁLCULO DE DEMOGRAFIA (Usando o nome normalizado) ---
        List<ItemContagemDTO> sexoBruto = lattesRepo.contarDistribuicaoSexoDaUniversidade(nomeLattesProcessado);
        List<DemografiaDTO> distribuicaoSexo = calcularPorcentagemDemografia(sexoBruto);

        List<ItemContagemDTO> racaBruta = lattesRepo.contarDistribuicaoRacaDaUniversidade(nomeLattesProcessado);
        List<DemografiaDTO> distribuicaoRaca = calcularPorcentagemDemografia(racaBruta);

        // 4. MONTANDO O DTO FINAL (Continua igual...)
        return new ComparacaoUniversidadeDTO(
                nomeUniversidade,
                totalArtigos, 
                totalCitacoes,
                totalDocentes, 
                faixaEtaria, 
                topAreas,
                List.of(), 
                List.of(), 
                List.of(), 
                totalFormados, 
                topPaises, 
                List.of(), 
                List.of(), 
                List.of(), 
                List.of(), 
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

    // Adicione este método na classe
    public List<String> buscarSugestoes(String termo) {
        if (termo == null || termo.trim().length() < 3) {
            return List.of();
        }
        return openAlexRepo.buscarSugestoesLimpas(termo);
    }

    private String normalizarParaLattes(String nome) {
        if (nome == null) return null;
        
        // Remove os acentos (São -> Sao)
        String normalizado = Normalizer.normalize(nome, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String semAcento = pattern.matcher(normalizado).replaceAll("");

        // Trata os casos gigantes explícitos
        if (semAcento.equalsIgnoreCase("Universidade de Sao Paulo")) return "%Universidade de Sao Paulo%";
        if (semAcento.equalsIgnoreCase("Universidade Federal de Sao Paulo")) return "%Universidade Federal de Sao Paulo%";
        
        // Coloca o % de ambos os lados para lidar com "1- Nome" e "Nome - Sigla"
        return "%" + semAcento + "%";
    }
    
}