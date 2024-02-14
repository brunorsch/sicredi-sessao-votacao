package io.github.brunorsch.sicredi.sessao.votacao.config;

import static io.github.brunorsch.sicredi.sessao.votacao.messaging.Topicos.RESULTADO_VOTACAO;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic resultadoVotacao() {
        return new NewTopic(RESULTADO_VOTACAO.getNome(), 1, (short) 1);
    }
}