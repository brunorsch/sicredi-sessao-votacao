package io.github.brunorsch.sicredi.sessao.votacao.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CriarPautaRequest;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Pauta;
import io.github.brunorsch.sicredi.sessao.votacao.testutils.Random;

class PautaMapperTest {
    private final PautaMapper pautaMapper = new PautaMapperImpl();

    @Test
    void fromCriarPautaRequestDeveMapearCorretamente() {
        var request = Random.obj(CriarPautaRequest.class);

        var resultado = pautaMapper.fromCriarPautaRequest(request);

        assertEquals(request.getTitulo(), resultado.getTitulo());
        assertEquals(request.getDescricao(), resultado.getDescricao());
    }

    @Test
    void toPautaResponseDeveMapearCorretamente() {
        var pauta = Random.obj(Pauta.class);

        var resultado = pautaMapper.toPautaResponse(pauta);

        assertEquals(pauta.getId(), resultado.getId());
        assertEquals(pauta.getTitulo(), resultado.getTitulo());
        assertEquals(pauta.getDescricao(), resultado.getDescricao());
        assertEquals(pauta.getDataHoraFimVotacao(), resultado.getDataHoraFimVotacao());
    }
}