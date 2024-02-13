package io.github.brunorsch.sicredi.sessao.votacao.exception;

import io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro;

public class AssociadoNaoEncontradoException extends RegraVioladaException {
    public AssociadoNaoEncontradoException() {
        super(CodigoErro.ASSOCIADO_NAO_ENCONTRADO);
    }
}