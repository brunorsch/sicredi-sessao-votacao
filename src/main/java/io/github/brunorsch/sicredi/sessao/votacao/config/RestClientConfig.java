package io.github.brunorsch.sicredi.sessao.votacao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientConfig {
    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}