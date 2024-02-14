package io.github.brunorsch.sicredi.sessao.votacao.mapper;


import static io.github.brunorsch.sicredi.sessao.votacao.utils.MascaramentoUtils.mascararCpf;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.AssociadoResponse;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Associado;
import io.github.brunorsch.sicredi.sessao.votacao.testutils.Random;

class AssociadoMapperTest {
    private static final AssociadoMapper mapper = new AssociadoMapperImpl();

    @Test
    void deveMapearCorretamente() {
        final var associado = Random.obj(Associado.class);

        final AssociadoResponse associadoResponse = mapper.toAssociadoResponse(associado);

        assertEquals(associado.getId(), associadoResponse.getId());
        assertEquals(mascararCpf(associado.getCpf()), associadoResponse.getCpf());
    }
}