package io.github.brunorsch.sicredi.sessao.votacao.service;

import static io.github.brunorsch.sicredi.sessao.votacao.utils.MascaramentoUtils.mascararCpf;
import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CadastrarAssociadoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.data.repository.AssociadoRepository;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Associado;
import io.github.brunorsch.sicredi.sessao.votacao.exception.AssociadoJaCadastradoException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.AssociadoNaoEncontradoException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.AssociadoNaoEstaAptoException;
import io.github.brunorsch.sicredi.sessao.votacao.integration.WhitelistIntegration;
import io.github.brunorsch.sicredi.sessao.votacao.mapper.AssociadoMapper;
import io.github.brunorsch.sicredi.sessao.votacao.mapper.AssociadoMapperImpl;
import io.github.brunorsch.sicredi.sessao.votacao.testutils.Random;

@ExtendWith(MockitoExtension.class)
class CrudAssociadoServiceTest {
    @InjectMocks
    private CrudAssociadoService service;

    @Mock
    private AssociadoRepository repository;

    @Spy
    private AssociadoMapper mapper = new AssociadoMapperImpl();

    @Mock
    private WhitelistIntegration whitelistIntegration;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "isWhitelistAtiva", true);
    }

    @Test
    void buscarDeveLancarAssociadoNaoEncontradoExceptionQuandoAssociadoNaoExistir() {
        when(repository.findById(anyLong()))
            .thenReturn(Optional.empty());

        assertThrows(AssociadoNaoEncontradoException.class, () -> service.buscar(nextLong()));
    }

    @Test
    void buscarDeveRetornarAssociadoCorretamente() {
        final var associado = Random.obj(Associado.class);
        when(repository.findById(anyLong()))
            .thenReturn(Optional.of(associado));

        final var resultado = service.buscar(nextLong());

        assertEquals(associado, resultado);
    }

    @Test
    void cadastrarDeveIgnorarWhitelistQuandoWhitelistDesativada() {
        ReflectionTestUtils.setField(service, "isWhitelistAtiva", false);
        final var request = Random.obj(CadastrarAssociadoRequest.class);

        service.cadastrar(request);

        verifyNoInteractions(whitelistIntegration);
    }

    @Test
    void cadastrarDeveLancarAssociadoNaoEstaAptoExceptionQuandoWhitelistAtivaEAssociadoNaoEstaApto() {
        final var request = Random.obj(CadastrarAssociadoRequest.class);
        when(whitelistIntegration.isCpfApto(request.getCpf()))
            .thenReturn(false);

        assertThrows(AssociadoNaoEstaAptoException.class, () -> service.cadastrar(request));
    }

    @Test
    void cadastrarDeveLancarAssociadoJaCadastradoExceptionQuandoAssociadoJaExistir() {
        final var request = Random.obj(CadastrarAssociadoRequest.class);
        when(whitelistIntegration.isCpfApto(request.getCpf()))
            .thenReturn(true);
        when(repository.existsByCpf(request.getCpf()))
            .thenReturn(true);

        assertThrows(AssociadoJaCadastradoException.class, () -> service.cadastrar(request));
    }

    @Test
    void cadastrarDeveCadastrarCorretamente() {
        final var request = Random.obj(CadastrarAssociadoRequest.class);
        final var associadoCadastrado = Random.obj(Associado.class);
        final var associadoMapeado = new Associado();
        associadoMapeado.setCpf(mascararCpf(request.getCpf()));

        when(repository.save(associadoMapeado))
            .thenReturn(associadoCadastrado);
        when(whitelistIntegration.isCpfApto(request.getCpf()))
            .thenReturn(true);

        final var resultado = service.cadastrar(request);

        assertEquals(mapper.toAssociadoResponse(associadoCadastrado), resultado);
    }
}