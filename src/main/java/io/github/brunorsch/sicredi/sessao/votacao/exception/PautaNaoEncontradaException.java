package io.github.brunorsch.sicredi.sessao.votacao.exception;

import static io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro.PAUTA_NAO_ENCONTRADA;

import io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro;

public class PautaNaoEncontradaException extends RegraVioladaException {
    public PautaNaoEncontradaException() {
        super(PAUTA_NAO_ENCONTRADA);
    }
}