package io.github.brunorsch.sicredi.sessao.votacao.exception;

import static io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro.ASSOCIADO_NAO_ENCONTRADO;

public class AssociadoNaoEncontradoException extends RegraVioladaException {
    public AssociadoNaoEncontradoException() {
        super(ASSOCIADO_NAO_ENCONTRADO);
    }
}