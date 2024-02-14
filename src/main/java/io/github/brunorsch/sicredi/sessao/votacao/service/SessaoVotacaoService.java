package io.github.brunorsch.sicredi.sessao.votacao.service;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNullElse;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.VotoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.data.projection.ApuracaoProjection;
import io.github.brunorsch.sicredi.sessao.votacao.data.repository.PautaRepository;
import io.github.brunorsch.sicredi.sessao.votacao.data.repository.VotoRepository;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Opcao;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Pauta;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Voto;
import io.github.brunorsch.sicredi.sessao.votacao.exception.ApuracaoJaRealizadaException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.DataHoraDeveSerFuturoException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.PautaJaPossuiSessaoException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.SessaoAindaEmAndamentoException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.SessaoJaEncerradaException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.SessaoNaoAbertaException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.VotoJaRealizadoException;
import io.github.brunorsch.sicredi.sessao.votacao.messaging.payload.ResultadoVotacaoPayload;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessaoVotacaoService {
    private final CrudAssociadoService crudAssociadoService;
    private final CrudPautaService crudPautaService;
    private final Clock clock;
    private final PautaRepository pautaRepository;
    private final VotoRepository votoRepository;
    private final MensageriaService mensageriaService;

    public void abrir(final Long idPauta, final LocalDateTime dataHoraFimVotacao) {
        log.info("Abrindo sessão de votação da pauta ID: {}", idPauta);
        log.debug("Data e hora informada para o fim da votaçao: {}", dataHoraFimVotacao);

        final var dataHoraAtual = LocalDateTime.now(clock);

        validarDataHoraInformadaAbertura(dataHoraFimVotacao, dataHoraAtual);

        final var dataHoraEfetiva = requireNonNullElse(dataHoraFimVotacao, dataHoraAtual.plusMinutes(1L));

        final var pauta = crudPautaService.buscar(idPauta);

        if (pauta.isSessaoIniciada()) {
            throw new PautaJaPossuiSessaoException();
        }

        pauta.setDataHoraFimVotacao(dataHoraEfetiva);

        pautaRepository.save(pauta);
    }

    private void validarDataHoraInformadaAbertura(
        final LocalDateTime dataHoraInformada, final LocalDateTime dataHoraAtual) {
        if (nonNull(dataHoraInformada) && !dataHoraAtual.isBefore(dataHoraInformada)) {
            throw new DataHoraDeveSerFuturoException();
        }
    }

    public void registrarVoto(final Long idPauta, final VotoRequest request) {
        log.info("Registrando voto do associado ID {} na pauta ID {}", request.getIdAssociado(), idPauta);
        log.debug("Opção do voto: {}", request.getOpcao());

        final var pauta = crudPautaService.buscar(idPauta);

        validarEstadoPautaParaVoto(pauta);
        validarVotoJaRealizado(idPauta, request.getIdAssociado());

        final var associado = crudAssociadoService.buscar(request.getIdAssociado());

        Voto voto = new Voto();
        voto.setAssociado(associado);
        voto.setPauta(pauta);
        voto.setOpcao(request.getOpcao());

        votoRepository.save(voto);
    }

    private void validarEstadoPautaParaVoto(final Pauta pauta) {
        final var dataHoraAtual = LocalDateTime.now(clock);
        if (!pauta.isSessaoIniciada()) {
            log.warn("Sessão de votação não está aberta");
            throw new SessaoNaoAbertaException();
        }

        if (!pauta.isSessaoEmAndamento(dataHoraAtual)) {
            log.warn("Sessão de votação já foi encerrada");
            throw new SessaoJaEncerradaException();
        }
    }

    private void validarVotoJaRealizado(final Long idPauta, final Long idAssociado) {
        if (votoRepository.existsByAssociadoIdAndPautaId(idAssociado, idPauta)) {
            log.warn("Associado já votou nesta pauta");
            throw new VotoJaRealizadoException();
        }
    }

    @Transactional
    public void apurarResultados(final Pauta pauta) {
        log.info("Apurando resultados da pauta ID {}", pauta.getId());

        validarEstadoPautaParaApuracao(pauta);

        final Map<Opcao, Long> votosPorOpcao = votoRepository.contarVotosPorIdPauta(pauta.getId())
            .stream()
            .collect(Collectors.toMap(ApuracaoProjection::getOpcao, ApuracaoProjection::getTotal));

        pauta.setVotacaoApurada(true);
        pautaRepository.save(pauta);

        final var eventoResultado = ResultadoVotacaoPayload.builder()
            .idPauta(pauta.getId())
            .votosSim(votosPorOpcao.get(Opcao.SIM))
            .votosNao(votosPorOpcao.get(Opcao.NAO))
            .build();

        mensageriaService.publicarEventoResultadoVotacao(eventoResultado);
    }

    private void validarEstadoPautaParaApuracao(final Pauta pauta) {
        if(!pauta.isSessaoIniciada()) {
            log.warn("Sessão de votação não foi aberta");
            throw new SessaoNaoAbertaException();
        }

        if (pauta.isSessaoEmAndamento(LocalDateTime.now(clock))) {
            log.warn("Votação ainda está em andamento");
            throw new SessaoAindaEmAndamentoException();
        }

        if (pauta.isVotacaoApurada()) {
            log.debug("Votação já foi apurada");
            throw new ApuracaoJaRealizadaException();
        }
    }
}