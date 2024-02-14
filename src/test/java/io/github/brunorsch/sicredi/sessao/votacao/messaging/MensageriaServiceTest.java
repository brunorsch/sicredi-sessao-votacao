package io.github.brunorsch.sicredi.sessao.votacao.messaging;

import static io.github.brunorsch.sicredi.sessao.votacao.messaging.Topicos.RESULTADO_VOTACAO;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import io.github.brunorsch.sicredi.sessao.votacao.messaging.payload.ResultadoVotacaoPayload;
import io.github.brunorsch.sicredi.sessao.votacao.testutils.Random;

@ExtendWith(MockitoExtension.class)
class MensageriaServiceTest {
    @InjectMocks
    private MensageriaService mensageriaService;

    @Mock
    private KafkaTemplate<String, ResultadoVotacaoPayload> kafkaTemplate;

    @Test
    public void devePublicarEventoResultadoVotacaoCorretamente() {
        ResultadoVotacaoPayload resultadoVotacao = Random.obj(ResultadoVotacaoPayload.class);

        mensageriaService.publicarEventoResultadoVotacao(resultadoVotacao);

        verify(kafkaTemplate).send(RESULTADO_VOTACAO.getNome(), resultadoVotacao);
    }
}