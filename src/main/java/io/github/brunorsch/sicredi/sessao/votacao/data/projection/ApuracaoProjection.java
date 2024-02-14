package io.github.brunorsch.sicredi.sessao.votacao.data.projection;

import io.github.brunorsch.sicredi.sessao.votacao.domain.Opcao;

public interface ApuracaoProjection {
    Opcao getOpcao();
    Long getTotal();
}