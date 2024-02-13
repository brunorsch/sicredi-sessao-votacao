package io.github.brunorsch.sicredi.sessao.votacao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CriarPautaRequest;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Pauta;
import io.github.brunorsch.sicredi.sessao.votacao.mapper.PautaMapper;
import io.github.brunorsch.sicredi.sessao.votacao.mapper.PautaMapperImpl;
import io.github.brunorsch.sicredi.sessao.votacao.repository.PautaRepository;
import io.github.brunorsch.sicredi.sessao.votacao.testutils.Random;

@ExtendWith(MockitoExtension.class)
class CrudPautaServiceTest {
    private CrudPautaService service;

    @Mock(stubOnly = true)
    private PautaRepository repository;

    private PautaMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PautaMapperImpl();
        service = new CrudPautaService(mapper, repository);
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