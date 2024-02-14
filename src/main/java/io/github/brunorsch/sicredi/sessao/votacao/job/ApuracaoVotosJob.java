package io.github.brunorsch.sicredi.sessao.votacao.job;

import static java.util.concurrent.TimeUnit.MINUTES;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.github.brunorsch.sicredi.sessao.votacao.data.repository.PautaRepository;
import io.github.brunorsch.sicredi.sessao.votacao.service.SessaoVotacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApuracaoVotosJob {
    private final PautaRepository pautaRepository;
    private final SessaoVotacaoService sessaoVotacaoService;

    @Scheduled(fixedRateString = "${app.intervalo-job-minutos}", timeUnit = MINUTES)
    public void job() {
        log.debug("Iniciando job para apuração de votos");

        pautaRepository.buscarTodasComSessaoFinalizadaEVotacaoNaoApurada()
            .forEach(sessaoVotacaoService::apurarResultados);
    }
}