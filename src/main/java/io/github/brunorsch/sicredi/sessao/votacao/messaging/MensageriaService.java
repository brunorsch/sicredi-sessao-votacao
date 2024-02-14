package io.github.brunorsch.sicredi.sessao.votacao.messaging;

import static io.github.brunorsch.sicredi.sessao.votacao.messaging.Topicos.RESULTADO_VOTACAO;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import io.github.brunorsch.sicredi.sessao.votacao.messaging.payload.ResultadoVotacaoPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MensageriaService {
    private final KafkaTemplate<String, ResultadoVotacaoPayload> kafkaTemplate;

    public void publicarEventoResultadoVotacao(final ResultadoVotacaoPayload payload) {
        log.info("Propagando evento de resultado da votação: {}", payload);

        kafkaTemplate.send(RESULTADO_VOTACAO.getNome(), payload);
    }
}