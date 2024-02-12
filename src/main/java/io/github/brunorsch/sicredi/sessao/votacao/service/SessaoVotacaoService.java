package io.github.brunorsch.sicredi.sessao.votacao.service;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNullElse;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import io.github.brunorsch.sicredi.sessao.votacao.exception.DataHoraDeveSerFuturoException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.PautaJaPossuiSessaoException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.PautaNaoEncontradaException;
import io.github.brunorsch.sicredi.sessao.votacao.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessaoVotacaoService {
    private final PautaRepository pautaRepository;
    private final Clock clock;

    public void abrir(final Long idPauta, final LocalDateTime dataHoraFimVotacao) {
        log.info("Abrindo sessão de votação da pauta ID: {}", idPauta);
        log.debug("Data e hora informada para o fim da votaçao: {}", dataHoraFimVotacao);

        var dataHoraAtual = LocalDateTime.now(clock);

        validarDataHoraInformada(dataHoraFimVotacao, dataHoraAtual);

        var dataHoraEfetiva = requireNonNullElse(dataHoraFimVotacao, dataHoraAtual.plusMinutes(1L));

        var pauta = pautaRepository.findById(idPauta)
            .orElseThrow(PautaNaoEncontradaException::new);

        if(nonNull(pauta.getDataHoraFimVotacao())) {
            throw new PautaJaPossuiSessaoException();
        }

        pauta.setDataHoraFimVotacao(dataHoraEfetiva);

        pautaRepository.save(pauta);
    }

    private void validarDataHoraInformada(final LocalDateTime dataHoraInformada, final LocalDateTime dataHoraAtual) {
        if(nonNull(dataHoraInformada) &&
            (dataHoraInformada.isEqual(dataHoraAtual) || dataHoraInformada.isBefore(dataHoraAtual))) {
            throw new DataHoraDeveSerFuturoException();
        }
    }
}