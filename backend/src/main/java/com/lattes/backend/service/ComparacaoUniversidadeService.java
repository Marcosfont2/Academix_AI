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
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

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
        
        // --- OPENALEX ---
        CompletableFuture<List<InstituicaoOpenAlex>> openAlexOptFuture = CompletableFuture.supplyAsync(() -> 
            openAlexRepo.buscarPorNome(nomeUniversidade)
        );

        // --- CAPES ---
        // Transforma "Universidade Federal de São Paulo" em "UNIVERSIDADE FEDERAL DE SÃO PAULO"
        String nomeCapesProcessado = nomeUniversidade.toUpperCase();

        CompletableFuture<Long> totalDocentesFuture = CompletableFuture.supplyAsync(() -> 
            capesRepo.contarTotalDocentesDaUniversidade(nomeCapesProcessado)
        );
        CompletableFuture<List<ItemContagemDTO>> faixaEtariaFuture = CompletableFuture.supplyAsync(() -> 
            capesRepo.contarDistribuicaoFaixaEtariaDaUniversidade(nomeCapesProcessado)
        );
        CompletableFuture<List<ItemContagemDTO>> topAreasFuture = CompletableFuture.supplyAsync(() -> 
            capesRepo.contarTopAreasAvaliacaoDaUniversidade(nomeCapesProcessado)
        );
        CompletableFuture<List<ItemContagemDTO>> grandesAreasFuture = CompletableFuture.supplyAsync(() -> 
            capesRepo.contarTopGrandesAreasDaUniversidade(nomeCapesProcessado)
        );
        CompletableFuture<List<ItemContagemDTO>> conceitoFuture = CompletableFuture.supplyAsync(() -> 
            capesRepo.contarDistribuicaoConceitoProgramaDaUniversidade(nomeCapesProcessado)
        );

        // --- LATTES ---
        String nomeLattesProcessado = normalizarParaLattes(nomeUniversidade);

        CompletableFuture<Long> totalFormadosFuture = CompletableFuture.supplyAsync(() -> 
            lattesRepo.contarTotalFormadosDaUniversidade(nomeLattesProcessado)
        );
        CompletableFuture<List<ItemContagemDTO>> topPaisesFuture = CompletableFuture.supplyAsync(() -> 
            lattesRepo.contarTopPaisesNascimentoDaUniversidade(nomeLattesProcessado)
        );
        CompletableFuture<List<ItemContagemDTO>> instituicoesAtuacaoFuture = CompletableFuture.supplyAsync(() -> 
            lattesRepo.contarTopInstituicoesAtuacaoDaUniversidade(nomeLattesProcessado)
        );
        CompletableFuture<List<ItemContagemDTO>> paisesAtuacaoFuture = CompletableFuture.supplyAsync(() -> 
            lattesRepo.contarTopPaisesAtuacaoDaUniversidade(nomeLattesProcessado)
        );
        CompletableFuture<List<ItemContagemDTO>> sexoBrutoFuture = CompletableFuture.supplyAsync(() -> 
            lattesRepo.contarDistribuicaoSexoDaUniversidade(nomeLattesProcessado)
        );
        CompletableFuture<List<ItemContagemDTO>> racaBrutaFuture = CompletableFuture.supplyAsync(() -> 
            lattesRepo.contarDistribuicaoRacaDaUniversidade(nomeLattesProcessado)
        );
        
        // O .join() faz o código "esperar" até que todos os métodos assíncronos voltem com o resultado.
        List<InstituicaoOpenAlex> openAlexOpt = openAlexOptFuture.join();
        InstituicaoOpenAlex instituicao = openAlexOpt.isEmpty() ? null : openAlexOpt.get(0);
        Integer totalArtigos = (instituicao != null) ? instituicao.getWorksCount() : 0;
        Integer totalCitacoes = (instituicao != null) ? instituicao.getCitedByCount() : 0;

        Long totalDocentes = totalDocentesFuture.join();
        if (totalDocentes == null) totalDocentes = 0L;

        Long totalFormados = totalFormadosFuture.join();
        if (totalFormados == null) totalFormados = 0L;

        // Calculamos a demografia aqui embaixo com os dados que já chegaram
        List<DemografiaDTO> distribuicaoSexo = calcularPorcentagemDemografia(sexoBrutoFuture.join());
        List<DemografiaDTO> distribuicaoRaca = calcularPorcentagemDemografia(racaBrutaFuture.join());
        
        return ComparacaoUniversidadeDTO.builder()
            .nomeUniversidade(nomeUniversidade)
            .totalArtigos(totalArtigos)
            .totalCitacoes(totalCitacoes)
            .totalDocentes(totalDocentes)
            .distribuicaoFaixaEtaria(faixaEtariaFuture.join())
            .topAreasAvaliacao(topAreasFuture.join())
            .topGrandesAreasConhecimento(grandesAreasFuture.join())
            .distribuicaoConceitoPrograma(conceitoFuture.join())
            .totalFormados(totalFormados)
            .topPaisesNascimento(topPaisesFuture.join())
            .topInstituicoesAtuacao(instituicoesAtuacaoFuture.join())
            .topPaisesAtuacao(paisesAtuacaoFuture.join())
            .distribuicaoSexo(distribuicaoSexo)
            .distribuicaoRaca(distribuicaoRaca)
            .build();
    }

    // Algoritmo nativo para processar a porcentagem matemática com base no total do grupo.
    private List<DemografiaDTO> calcularPorcentagemDemografia(List<ItemContagemDTO> dadosBrutos) {
        if (dadosBrutos == null || dadosBrutos.isEmpty()) {
            return List.of();
        }

        // Descobre o 100% (Soma de todos os registros daquela lista)
        long totalGeral = dadosBrutos.stream()
                .mapToLong(ItemContagemDTO::quantidade)
                .sum();

        if (totalGeral == 0) return List.of();

        // Mapeia cada item transformando em DemografiaDTO com a porcentagem
        return dadosBrutos.stream().map(item -> {
            double porcentagemExata = (item.quantidade() * 100.0) / totalGeral;
            // Arredonda para 2 casas decimais (ex: 33.33)
            double porcentagemArredondada = Math.round(porcentagemExata * 100.0) / 100.0;
            
            return DemografiaDTO.builder().categoria(item.chave()).totalAbsoluto(item.quantidade()).porcentagem(porcentagemArredondada).build();
        }).toList();
    }

    // Método que busca os nomes das universidades para o autocomplete do frontend. Ele é chamado a cada letra digitada.
    // Por isso, a busca é feita diretamente no banco de dados usando uma query otimizada.
    // Na tabela de instituições do openalex, os nomes já estão limpos.
    public List<String> buscarSugestoes(String termo) {
        // Só começamos a buscar sugestões quando o usuário digitar pelo menos 3 caracteres.
        if (termo == null || termo.trim().length() < 3) {
            return List.of();
        }
        return openAlexRepo.buscarSugestoesLimpas(termo);
    }

    // Transforma "Universidade de São Paulo" em "%Universidade de Sao Paulo%" para o Lattes encontrar
    // variações como "1- Universidade de São Paulo" ou "Universidade de São Paulo - USP"
    private String normalizarParaLattes(String nome) {
        if (nome == null) return null;
        
        // Remove os acentos (São -> Sao)
        String normalizado = Normalizer.normalize(nome, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String semAcento = pattern.matcher(normalizado).replaceAll("");
        
        // Coloca o % de ambos os lados para lidar com "1- Nome" e "Nome - Sigla"
        return "%" + semAcento + "%";
    }
    
}