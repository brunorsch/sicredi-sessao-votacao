package io.github.brunorsch.sicredi.sessao.votacao.service;

import static java.util.Optional.empty;
import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CriarPautaRequest;
import io.github.brunorsch.sicredi.sessao.votacao.data.repository.PautaRepository;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Pauta;
import io.github.brunorsch.sicredi.sessao.votacao.exception.PautaNaoEncontradaException;
import io.github.brunorsch.sicredi.sessao.votacao.mapper.PautaMapper;
import io.github.brunorsch.sicredi.sessao.votacao.mapper.PautaMapperImpl;
import io.github.brunorsch.sicredi.sessao.votacao.testutils.Random;

@ExtendWith(MockitoExtension.class)
class CrudPautaServiceTest {
    @InjectMocks
    private CrudPautaService service;

    @Mock(stubOnly = true)
    private PautaRepository repository;

    @Spy
    private PautaMapper mapper = new PautaMapperImpl();

    @Test
    void buscarDeveLancarExceptionQuandoPautaNaoExistir() {
        final var id = nextLong();

        when(repository.findById(id))
            .thenReturn(empty());

        assertThrows(PautaNaoEncontradaException.class, () -> service.buscar(id));
    }

    @Test
    void buscarDeveRetornarPautaQuandoExistir() {
        final var id = nextLong();
        final var pautaEsperada = Random.obj(Pauta.class);
        pautaEsperada.setId(id);

        when(repository.findById(id))
            .thenReturn(Optional.of(pautaEsperada));

        final var pauta = service.buscar(id);

        assertEquals(pautaEsperada, pauta);
    }

    @Test
    void criarDeveSalvarNovaPautaCorretamente() {
        var request = Random.obj(CriarPautaRequest.class);
        var requestMapeada = mapper.fromCriarPautaRequest(request);
        var pautaEsperada = Random.obj(Pauta.class);
        var responseEsperada = mapper.toPautaResponse(pautaEsperada);

        when(repository.save(requestMapeada))
            .thenReturn(pautaEsperada);

        var created = service.criar(request);

        assertEquals(responseEsperada, created);
    }

}