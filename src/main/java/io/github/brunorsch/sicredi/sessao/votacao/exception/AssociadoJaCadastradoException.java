package io.github.brunorsch.sicredi.sessao.votacao.exception;

import static io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro.ASSOCIADO_JA_CADASTRADO;

public class AssociadoJaCadastradoException extends RegraVioladaException {
    public AssociadoJaCadastradoException() {
        super(ASSOCIADO_JA_CADASTRADO);
    }
}