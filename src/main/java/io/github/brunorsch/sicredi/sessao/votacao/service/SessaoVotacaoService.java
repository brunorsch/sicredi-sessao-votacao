package io.github.brunorsch.sicredi.sessao.votacao.service;

import static io.github.brunorsch.sicredi.sessao.votacao.utils.LocalDateTimeUtils.isFuturoOuPresente;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNullElse;

import java.time.Clock;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.VotoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Pauta;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Voto;
import io.github.brunorsch.sicredi.sessao.votacao.exception.DataHoraDeveSerFuturoException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.PautaJaPossuiSessaoException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.PautaNaoEncontradaException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.SessaoJaEncerradaException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.SessaoNaoAbertaException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.VotoJaRealizadoException;
import io.github.brunorsch.sicredi.sessao.votacao.repository.PautaRepository;
import io.github.brunorsch.sicredi.sessao.votacao.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessaoVotacaoService {
    private final CrudAssociadoService crudAssociadoService;
    private final Clock clock;
    private final PautaRepository pautaRepository;
    private final VotoRepository votoRepository;

    public void abrir(final Long idPauta, final LocalDateTime dataHoraFimVotacao) {
        log.info("Abrindo sessão de votação da pauta ID: {}", idPauta);
        log.debug("Data e hora informada para o fim da votaçao: {}", dataHoraFimVotacao);

        final var dataHoraAtual = LocalDateTime.now(clock);

        validarDataHoraInformadaAbertura(dataHoraFimVotacao, dataHoraAtual);

        final var dataHoraEfetiva = requireNonNullElse(dataHoraFimVotacao, dataHoraAtual.plusMinutes(1L));

        final var pauta = pautaRepository.findById(idPauta)
            .orElseThrow(PautaNaoEncontradaException::new);

        if(nonNull(pauta.getDataHoraFimVotacao())) {
            throw new PautaJaPossuiSessaoException();
        }

        pauta.setDataHoraFimVotacao(dataHoraEfetiva);

        pautaRepository.save(pauta);
    }

    private void validarDataHoraInformadaAbertura(
        final LocalDateTime dataHoraInformada, final LocalDateTime dataHoraAtual) {
        if(nonNull(dataHoraInformada) && !dataHoraAtual.isBefore(dataHoraInformada)) {
            throw new DataHoraDeveSerFuturoException();
        }
    }

    public void registrarVoto(final Long idPauta, final VotoRequest request) {
        log.info("Registrando voto do associado ID {} na pauta ID {}", request.getIdAssociado(), idPauta);
        log.debug("Opção do voto: {}", request.getOpcao());

        final var pauta = pautaRepository.findById(idPauta)
            .orElseThrow(PautaNaoEncontradaException::new);

        validarEstadoSessao(pauta);
        validarVotoJaRealizado(idPauta, request.getIdAssociado());

        final var associado = crudAssociadoService.buscar(request.getIdAssociado());

        Voto voto = new Voto();
        voto.setAssociado(associado);
        voto.setPauta(pauta);
        voto.setOpcao(request.getOpcao());

        votoRepository.save(voto);
    }

    private void validarEstadoSessao(final Pauta pauta) {
        final var dataHoraAtual = LocalDateTime.now(clock);
        if(isNull(pauta.getDataHoraFimVotacao())) {
            log.warn("Sessão de votação não está aberta");
            throw new SessaoNaoAbertaException();
        }

        if(!isFuturoOuPresente(dataHoraAtual, pauta.getDataHoraFimVotacao())) {
            log.warn("Sessão de votação já foi encerrada");
            throw new SessaoJaEncerradaException();
        }
    }

    private void validarVotoJaRealizado(final Long idPauta, final Long idAssociado) {
        if(votoRepository.existsByAssociadoIdAndPautaId(idAssociado, idPauta)) {
            log.warn("Associado já votou nesta pauta");
            throw new VotoJaRealizadoException();
        }
    }
}