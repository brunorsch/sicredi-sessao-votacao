package io.github.brunorsch.sicredi.sessao.votacao.service;

import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.VotoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Associado;
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
import io.github.brunorsch.sicredi.sessao.votacao.testutils.Random;

@ExtendWith(MockitoExtension.class)
class SessaoVotacaoServiceTest {
    private SessaoVotacaoService service;

    @Mock
    private CrudAssociadoService crudAssociadoService;

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private VotoRepository votoRepository;

    private Clock clock;
    private Long idPauta;
    private LocalDateTime dataHoraFimVotacao;

    @BeforeEach
    void setUp() {
        clock = Clock.fixed(Instant.parse("2024-02-12T12:00:00Z"), ZoneId.systemDefault());
        service = new SessaoVotacaoService(crudAssociadoService, clock, pautaRepository, votoRepository);
        idPauta = nextLong();
        dataHoraFimVotacao = LocalDateTime.now(clock).plusMinutes(10L);
    }

    @Nested
    public class AbrirTests {
        @Captor
        private ArgumentCaptor<Pauta> pautaCaptor;

        @Test
        void deveLancarExceptionQuandoDataHoraFimVotacaoForAnteriorADataHoraAtual() {
            dataHoraFimVotacao = LocalDateTime.now(clock).minusMinutes(1L);

            assertThrows(DataHoraDeveSerFuturoException.class, () -> service.abrir(idPauta, dataHoraFimVotacao));
        }

        @Test
        void deveLancarExceptionQuandoDataHoraFimVotacaoForIgualADataHoraAtual() {
            dataHoraFimVotacao = LocalDateTime.now(clock);

            assertThrows(DataHoraDeveSerFuturoException.class, () -> service.abrir(idPauta, dataHoraFimVotacao));
        }

        @Test
        void deveLancarExceptionQuandoPautaNaoExistir() {
            when(pautaRepository.findById(idPauta))
                .thenReturn(Optional.empty());

            assertThrows(PautaNaoEncontradaException.class, () -> service.abrir(idPauta, dataHoraFimVotacao));
        }

        @Test
        void deveLancarExceptionQuandoPautaJaPossuirSessaoAberta() {
            mockConsultaPautaRepository();

            assertThrows(PautaJaPossuiSessaoException.class, () -> service.abrir(idPauta, dataHoraFimVotacao));
        }

        @Test
        void deveSalvarDataHoraFimVotacaoUmMinutoAFrenteQuandoDataHoraFimVotacaoForNula() {
            var pautaSalva = testSucessoComDataHora(null);

            var dataHoraFimVotacaoEsperada = LocalDateTime.now(clock).plusMinutes(1L);
            var dataHoraFimVotacaoAtual = pautaSalva.getDataHoraFimVotacao();

            assertEquals(dataHoraFimVotacaoEsperada, dataHoraFimVotacaoAtual);
        }

        @Test
        void deveSalvarDataHoraFimVotacaoInformadaCorretamente() {
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

    @Nested
    public class RegistrarVotoTests {
        @Captor
        private ArgumentCaptor<Voto> votoCaptor;

        @Test
        void deveLancarExceptionQuandoPautaNaoExistir() {
            when(pautaRepository.findById(idPauta))
                .thenReturn(Optional.empty());

            assertThrows(PautaNaoEncontradaException.class, () -> service.registrarVoto(idPauta, Random.obj(VotoRequest.class)));
        }

        @Test
        void deveLancarExceptionQuandoSessaoNaoEstiverAberta() {
            var pauta = mockConsultaPauta();
            pauta.setDataHoraFimVotacao(null);

            assertThrows(SessaoNaoAbertaException.class,
                () -> service.registrarVoto(idPauta, Random.obj(VotoRequest.class)));
        }

        @Test
        void deveLancarExceptionQuandoSessaoJaEstiverEncerrada() {
            final var pauta = mockConsultaPauta();
            pauta.setDataHoraFimVotacao(LocalDateTime.now(clock).minusMinutes(1L));

            assertThrows(SessaoJaEncerradaException.class,
                () -> service.registrarVoto(idPauta, Random.obj(VotoRequest.class)));
        }

        @Test
        void deveLancarExceptionQuandoVotoJaFoiRealizado() {
            mockConsultaPauta();

            var votoRequest = Random.obj(VotoRequest.class);
            when(votoRepository.existsByAssociadoIdAndPautaId(votoRequest.getIdAssociado(), idPauta))
                .thenReturn(true);

            assertThrows(VotoJaRealizadoException.class, () -> service.registrarVoto(idPauta, votoRequest));
        }

        @Test
        void deveRegistrarVotoCorretamente() {
            var pauta = mockConsultaPauta();
            var votoRequest = Random.obj(VotoRequest.class);
            var associado = Random.obj(Associado.class);
            associado.setId(votoRequest.getIdAssociado());
            when(crudAssociadoService.buscar(votoRequest.getIdAssociado()))
                .thenReturn(associado);

            service.registrarVoto(idPauta, votoRequest);

            verify(votoRepository).save(votoCaptor.capture());
            final var votoSalvo = votoCaptor.getValue();
            assertEquals(pauta, votoSalvo.getPauta());
            assertEquals(votoRequest.getIdAssociado(), votoSalvo.getAssociado().getId());
            assertEquals(votoRequest.getOpcao(), votoSalvo.getOpcao());
        }

        private Pauta mockConsultaPauta() {
            final var pauta = Random.obj(Pauta.class);
            pauta.setDataHoraFimVotacao(LocalDateTime.now(clock).plusMinutes(10L));
            when(pautaRepository.findById(idPauta))
                .thenReturn(Optional.of(pauta));

            return pauta;
        }
    }
}