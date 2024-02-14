package io.github.brunorsch.sicredi.sessao.votacao.exception;

import static io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro.APURACAO_JA_REALIZADA;

public class ApuracaoJaRealizadaException extends RegraVioladaException {
    public ApuracaoJaRealizadaException() {
        super(APURACAO_JA_REALIZADA);
    }
}