package io.github.brunorsch.sicredi.sessao.votacao.exception;

import static io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro.VOTO_JA_REALIZADO;

public class VotoJaRealizadoException extends RegraVioladaException {
    public VotoJaRealizadoException() {
        super(VOTO_JA_REALIZADO);
    }
}