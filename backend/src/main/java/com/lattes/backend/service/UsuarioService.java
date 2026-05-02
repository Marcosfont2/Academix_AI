package com.lattes.backend.service;

import com.lattes.backend.api.dto.UsuarioPublicoDTO;
import com.lattes.backend.domain.model.Usuario;
import com.lattes.backend.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

import com.lattes.backend.infra.exception.EntidadeNaoEncontradaException;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final EmailService emailService; // 1. Declarando a injeção de dependência

    // 2. Injetando no construtor
    public UsuarioService(UsuarioRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    public Optional<Usuario> autenticar(String email, String senha) {
        return repository.findByEmail(email)
                .filter(u -> u.getSenha().equals(senha)); // Futuramente aplicar BCrypt aqui
    }

    public void atualizarCurriculo(Long userId, String novoTexto) {
        Usuario usuario = repository.findById(userId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário com ID " + userId + " não encontrado."));
        
        usuario.setCurriculoTexto(novoTexto);
        repository.save(usuario); // Salva o texto no banco
        
        // NOVO: Dispara o e-mail confirmando o upload com sucesso
        emailService.enviarEmailAtualizacaoCurriculo(usuario.getEmail(), usuario.getNome());
    }

    public String buscarCurriculo(Long userId) {
        Usuario usuario = repository.findById(userId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário com ID " + userId + " não encontrado."));
        
        return usuario.getCurriculoTexto();
    }

    public Usuario registrar(Usuario novoUsuario) {
        // Regra de ouro: Não deixar criar dois usuários com o mesmo email
        if (repository.findByEmail(novoUsuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado!");
        }
        
        // 3. Salva no banco de dados primeiro
        Usuario usuarioSalvo = repository.save(novoUsuario);
        
        // 4. Dispara o e-mail de boas-vindas usando os dados salvos
        emailService.enviarEmailBoasVindas(usuarioSalvo.getEmail(), usuarioSalvo.getNome());
        
        return usuarioSalvo;
    }

    public void salvarCurriculoXml(Long userId, org.springframework.web.multipart.MultipartFile arquivo) throws Exception {
        // Lê todos os bytes do arquivo e transforma em String
        String conteudo = new String(arquivo.getBytes(), java.nio.charset.StandardCharsets.UTF_8);
        
        atualizarCurriculo(userId, conteudo);
    }

    public List<UsuarioPublicoDTO> listarUsuariosPublicos() {
    return repository.findAll().stream()
        .map(u -> {
            String textoOriginal = u.getCurriculoTexto();
            String resumo = "Pesquisador no Academix AI"; // Valor padrão

            if (textoOriginal != null && !textoOriginal.isBlank()) {
                // Limpa possíveis tags XML que sobraram para a bio ficar limpa
                String textoLimpo = textoOriginal.replaceAll("<[^>]*>", " ").trim();
                
                // Pega os primeiros 150 caracteres com segurança
                resumo = textoLimpo.length() > 150 
                    ? textoLimpo.substring(0, 150) + "..." 
                    : textoLimpo;
            }

            return UsuarioPublicoDTO.builder().id(u.getId()).nome(u.getNome()).descricaoCurta(resumo).build();
        })
        .collect(Collectors.toList());
    }

    public String buscarNomeUsuario(Long id) {
        return repository.findById(id)
            .map(u -> u.getNome())
            .orElse("Pesquisador");
    }
}