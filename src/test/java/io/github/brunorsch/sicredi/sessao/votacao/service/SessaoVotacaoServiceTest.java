package io.github.brunorsch.sicredi.sessao.votacao.service;

import static java.time.Clock.systemDefaultZone;
import static java.time.Instant.now;
import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.brunorsch.sicredi.sessao.votacao.domain.Pauta;
import io.github.brunorsch.sicredi.sessao.votacao.exception.DataHoraDeveSerFuturoException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.PautaJaPossuiSessaoException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.PautaNaoEncontradaException;
import io.github.brunorsch.sicredi.sessao.votacao.repository.PautaRepository;
import io.github.brunorsch.sicredi.sessao.votacao.testutils.Random;

@ExtendWith(MockitoExtension.class)
class SessaoVotacaoServiceTest {
    private SessaoVotacaoService service;

    @Mock
    private PautaRepository pautaRepository;

    @Captor
    private ArgumentCaptor<Pauta> pautaCaptor;

    private Clock clock;
    private Long idPauta;
    private LocalDateTime dataHoraFimVotacao;

    @BeforeEach
    void setUp() {
        clock = Clock.fixed(Instant.parse("2024-02-12T12:00:00Z"), ZoneId.systemDefault());
        service = new SessaoVotacaoService(pautaRepository, clock);
        idPauta = nextLong();
        dataHoraFimVotacao = LocalDateTime.now(clock).plusMinutes(10L);
    }

    @Test
    void abrirDeveLancarExceptionQuandoDataHoraFimVotacaoForAnteriorADataHoraAtual() {
        dataHoraFimVotacao = LocalDateTime.now(clock).minusMinutes(1L);

        assertThrows(DataHoraDeveSerFuturoException.class, () -> service.abrir(idPauta, dataHoraFimVotacao));
    }

    @Test
    void abrirDeveLancarExceptionQuandoDataHoraFimVotacaoForIgualADataHoraAtual() {
        dataHoraFimVotacao = LocalDateTime.now(clock);

        assertThrows(DataHoraDeveSerFuturoException.class, () -> service.abrir(idPauta, dataHoraFimVotacao));
    }

    @Test
    void abrirDeveLancarExceptionQuandoPautaNaoExistir() {
        when(pautaRepository.findById(idPauta))
            .thenReturn(Optional.empty());

        assertThrows(PautaNaoEncontradaException.class, () -> service.abrir(idPauta, dataHoraFimVotacao));
    }

    @Test
    void abrirDeveLancarExceptionQuandoPautaJaPossuirSessaoAberta() {
        mockConsultaPautaRepository();

        assertThrows(PautaJaPossuiSessaoException.class, () -> service.abrir(idPauta, dataHoraFimVotacao));
    }

    @Test
    void abrirDeveSalvarDataHoraFimVotacaoUmMinutoAFrenteQuandoDataHoraFimVotacaoForNula() {
        var pautaSalva = testSucessoComDataHora(null);

        var dataHoraFimVotacaoEsperada = LocalDateTime.now(clock).plusMinutes(1L);
        var dataHoraFimVotacaoAtual = pautaSalva.getDataHoraFimVotacao();

        assertEquals(dataHoraFimVotacaoEsperada, dataHoraFimVotacaoAtual);
    }

    @Test
    void abrirDeveSalvarDataHoraFimVotacaoInformadaCorretamente() {
        var pautaSalva = testSucessoComDataHora(dataHoraFimVotacao);

        assertEquals(dataHoraFimVotacao, pautaSalva.getDataHoraFimVotacao());
    }

    private Pauta mockConsultaPautaRepository() {
        var pauta = Random.obj(Pauta.class);
        when(pautaRepository.findById(idPauta))
            .thenReturn(Optional.of(pauta));

        return pauta;
    }

    private Pauta testSucessoComDataHora(final LocalDateTime dataHoraFimVotacao) {
        var pautaMockada = mockConsultaPautaRepository();
        pautaMockada.setDataHoraFimVotacao(null);

        service.abrir(idPauta, dataHoraFimVotacao);

        verify(pautaRepository).save(pautaCaptor.capture());

        return pautaCaptor.getValue();
    }
}