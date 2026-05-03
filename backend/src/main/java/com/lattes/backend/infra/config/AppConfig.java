package com.lattes.backend.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // Avisa o Spring: Leia isto quando o sistema ligar
public class AppConfig {

    @Bean // Ensina o Spring a fabricar o RestTemplate
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}