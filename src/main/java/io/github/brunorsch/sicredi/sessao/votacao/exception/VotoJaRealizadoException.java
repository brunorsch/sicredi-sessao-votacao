package io.github.brunorsch.sicredi.sessao.votacao.exception;

import io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro;

public class VotoJaRealizadoException extends RegraVioladaException {
    public VotoJaRealizadoException() {
        super(CodigoErro.SESSAO_NAO_ABERTA);
    }
}