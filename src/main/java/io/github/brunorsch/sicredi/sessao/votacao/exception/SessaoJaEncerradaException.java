package io.github.brunorsch.sicredi.sessao.votacao.exception;

import static io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro.SESSAO_JA_ENCERRADA;

public class SessaoJaEncerradaException extends RegraVioladaException {
    public SessaoJaEncerradaException() {
        super(SESSAO_JA_ENCERRADA);
    }
}