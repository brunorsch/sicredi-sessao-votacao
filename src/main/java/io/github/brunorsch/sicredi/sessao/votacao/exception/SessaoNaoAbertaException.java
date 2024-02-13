package io.github.brunorsch.sicredi.sessao.votacao.exception;

import static io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro.SESSAO_NAO_ABERTA;

public class SessaoNaoAbertaException extends RegraVioladaException {
    public SessaoNaoAbertaException() {
        super(SESSAO_NAO_ABERTA);
    }
}