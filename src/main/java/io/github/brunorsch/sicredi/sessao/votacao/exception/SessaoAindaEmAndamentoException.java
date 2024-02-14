package io.github.brunorsch.sicredi.sessao.votacao.exception;

import io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro;

public class SessaoAindaEmAndamentoException extends RegraVioladaException {
    public SessaoAindaEmAndamentoException() {
        super(CodigoErro.SESSAO_AINDA_EM_ANDAMENTO);
    }
}