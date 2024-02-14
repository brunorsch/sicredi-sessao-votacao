package io.github.brunorsch.sicredi.sessao.votacao.job;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.brunorsch.sicredi.sessao.votacao.data.repository.PautaRepository;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Pauta;
import io.github.brunorsch.sicredi.sessao.votacao.service.SessaoVotacaoService;
import io.github.brunorsch.sicredi.sessao.votacao.testutils.Random;

@ExtendWith(MockitoExtension.class)
class ApuracaoVotosJobTest {
    @InjectMocks
    private ApuracaoVotosJob apuracaoVotosJob;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private SessaoVotacaoService sessaoVotacaoService;

    @Test
    void jobDeveChamarServiceQuandoRepositoryRetornarPautas() {
        final var pautas = Stream.generate(() -> Random.obj(Pauta.class)).limit(10).toList();
        when(pautaRepository.buscarTodasComSessaoFinalizadaEVotacaoNaoApurada())
            .thenReturn(pautas);

        apuracaoVotosJob.job();

        pautas.forEach(pauta -> {
            verify(sessaoVotacaoService).apurarResultados(pauta);
        });
    }

    @Test
    void jobDeveIgnorarQuandoRepositoryNaoRetornarPautas() {
        when(pautaRepository.buscarTodasComSessaoFinalizadaEVotacaoNaoApurada())
            .thenReturn(emptyList());

        apuracaoVotosJob.job();

        verifyNoInteractions(sessaoVotacaoService);
    }
}