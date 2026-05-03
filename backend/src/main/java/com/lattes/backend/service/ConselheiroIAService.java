package com.lattes.backend.service;

import com.lattes.backend.domain.model.Usuario;
import com.lattes.backend.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.lattes.backend.infra.exception.EntidadeNaoEncontradaException;
import java.util.Map;

@Service
public class ConselheiroIAService {

    private final UsuarioRepository repository;
    private final RestTemplate restTemplate;

    // Puxa a chave secreta que você colocou no application.properties
    @Value("${gemini.api.key}")
    private String apiKey;

    // RestTemplate é injetado pelo Spring.
    public ConselheiroIAService(UsuarioRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    public String gerarConselhoCarreira(Long userId) {
        // Busca o usuário com tratamento de erro
        Usuario usuario = repository.findById(userId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado. Impossível gerar conselho."));

        // Se o usuário existir, mas não tiver currículo, também tratamos como recurso não encontrado
        if (usuario.getCurriculoTexto() == null) {
            throw new EntidadeNaoEncontradaException("Nenhum currículo encontrado para este usuário. Faça o upload na aba 'Meu Perfil' primeiro.");
        }

        String curriculo = usuario.getCurriculoTexto();

        // Monta o Prompt Mestre (Instruções para a IA)
        String prompt = "Você é um conselheiro acadêmico sênior, amigável e encorajador. " +
                "Leia o currículo do aluno abaixo (que pode estar em formato XML) e sugira o melhor próximo passo para a carreira dele. " +
                "Seja direto, prático e divida a resposta em tópicos curtos. " +
                "Currículo do aluno: " + curriculo;

        // Prepara a requisição no formato exato que a API do Gemini exige
        // O sufixo "-latest" costuma apontar para a versão que ainda está no ar na cota gratuita
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Montando o JSON usando Map do Java (evita ter que criar dezenas de DTOs só pra API do Google)
        Map<String, Object> body = Map.of(
            "contents", new Object[]{
                Map.of("parts", new Object[]{
                    Map.of("text", prompt)
                })
            }
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        // Faz a chamada para a internet e extrai a resposta
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            // Navega pelo JSON de resposta do Gemini para pegar apenas o texto gerado
            Map<String, Object> responseBody = response.getBody();
            java.util.List<Map<String, Object>> candidates = (java.util.List<Map<String, Object>>) responseBody.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            java.util.List<Map<String, Object>> parts = (java.util.List<Map<String, Object>>) content.get("parts");
            
            return (String) parts.get(0).get("text");
            
        } catch (Exception e) { // Se a internet do servidor cair ou ultrapassar a cota da API.
            //TODO: Atualizar para IntegraçãoAIException depois
            e.printStackTrace();
            return "Desculpe, ocorreu um erro ao se comunicar com o conselheiro IA no momento.";
        }
    }
}