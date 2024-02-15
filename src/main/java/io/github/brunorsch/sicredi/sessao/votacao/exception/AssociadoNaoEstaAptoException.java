package io.github.brunorsch.sicredi.sessao.votacao.exception;

import static io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro.ASSOCIADO_NAO_ESTA_APTO;

public class AssociadoNaoEstaAptoException extends RegraVioladaException {
    public AssociadoNaoEstaAptoException() {
        super(ASSOCIADO_NAO_ESTA_APTO);
    }
}