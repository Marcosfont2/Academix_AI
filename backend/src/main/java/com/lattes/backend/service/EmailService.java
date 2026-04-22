package com.lattes.backend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmailBoasVindas(String para, String nomeUsuario) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("Academix AI <seu-email@gmail.com>");
        message.setTo(para);
        message.setSubject("Bem-vindo ao Academix AI!");
        message.setText("Olá, " + nomeUsuario + "!\n\n" +
                "É um prazer ter você no Academix AI. Agora você pode gerenciar seu currículo, " +
                "analisar métricas acadêmicas e utilizar nossa IA para planejar seus próximos passos.\n\n" +
                "Explore o Dashboard e o Comparador de Universidades para começar.\n\n" +
                "Atenciosamente,\nEquipe Academix AI.");

        mailSender.send(message);
    }

    public void enviarEmailAtualizacaoCurriculo(String para, String nomeUsuario) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(para);
        message.setSubject("✅ Currículo Atualizado no Academix AI");
        message.setText("Olá, " + nomeUsuario + "!\n\n" +
                "O arquivo XML do seu currículo Lattes foi processado e salvo com sucesso na nossa base de dados.\n" +
                "O Conselheiro IA já está pronto para analisar sua trajetória com seus dados mais recentes!\n\n" +
                "Atenciosamente,\nEquipe Academix AI.");

        mailSender.send(message);
    }
}