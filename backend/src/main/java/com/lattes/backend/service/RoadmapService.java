package com.lattes.backend.service;

import com.lattes.backend.api.dto.AtividadeManualDTO;
import com.lattes.backend.api.dto.EventoTimelineDTO;
import com.lattes.backend.domain.model.AtividadeManual;
import com.lattes.backend.domain.repository.AtividadeManualRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RoadmapService {

    private final UsuarioService usuarioService;
    private final AtividadeManualRepository atividadeManualRepository;

    // Construtor atualizado: Inicializa os dois serviços (resolve o erro!)
    public RoadmapService(UsuarioService usuarioService, AtividadeManualRepository atividadeManualRepository) {
        this.usuarioService = usuarioService;
        this.atividadeManualRepository = atividadeManualRepository;
    }

    // Método que salva o que vem do formulário do React
    public void salvarAtividadeManual(Long userId, AtividadeManualDTO dto) {
        // Usando o padrão Builder para criar a nova atividade manual de forma mais legível
        AtividadeManual novaAtividade = AtividadeManual.builder()
            .usuarioId(userId)
            .ano(dto.ano())
            .tipo(dto.tipo())
            .titulo(dto.titulo())
            .descricao(dto.descricao())
            .build();
        atividadeManualRepository.save(novaAtividade);
    }

    // Método que constrói a Linha do Tempo
    public List<EventoTimelineDTO> gerarLinhaDoTempo(Long userId) {
        List<EventoTimelineDTO> eventos = new ArrayList<>();

        // 1. Busca as atividades manuais do Banco de Dados (Cursos, projetos)
        List<AtividadeManual> atividadesExtras = atividadeManualRepository.findByUsuarioId(userId);
        for (AtividadeManual extra : atividadesExtras) {
            eventos.add(new EventoTimelineDTO(
                extra.getAno(),
                extra.getTipo(),
                extra.getTitulo(),
                extra.getDescricao()
            ));
        }

        // 2. Busca o XML do Lattes
        String xml = usuarioService.buscarCurriculo(userId);
        
        // Se existir XML, faz o parser e junta com as atividades do banco
        if (xml != null && !xml.isBlank()) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

                // Extrair Formação
                NodeList graduacoes = doc.getElementsByTagName("GRADUACAO");
                for (int i = 0; i < graduacoes.getLength(); i++) {
                    Element el = (Element) graduacoes.item(i);
                    eventos.add(new EventoTimelineDTO(
                        Integer.parseInt(el.getAttribute("ANO-DE-CONCLUSAO")),
                        "Formação",
                        "Graduação em " + el.getAttribute("NOME-CURSO"),
                        el.getAttribute("NOME-INSTITUICAO")
                    ));
                }

                // Extrair Publicações
                NodeList artigos = doc.getElementsByTagName("DADOS-BASICOS-DO-ARTIGO");
                for (int i = 0; i < artigos.getLength(); i++) {
                    Element el = (Element) artigos.item(i);
                    eventos.add(new EventoTimelineDTO(
                        Integer.parseInt(el.getAttribute("ANO-DO-ARTIGO")),
                        "Publicação",
                        el.getAttribute("TITULO-DO-ARTIGO"),
                        "Artigo publicado em periódico/conferência"
                    ));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 3. Ordena tudo misturado (XML + Banco) do mais recente para o mais antigo
        eventos.sort(Comparator.comparing(EventoTimelineDTO::ano).reversed());
        return eventos;
    }
}