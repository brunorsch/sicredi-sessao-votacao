package io.github.brunorsch.sicredi.sessao.votacao.exception;

import static io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro.PAUTA_JA_POSSUI_SESSAO;

public class PautaJaPossuiSessaoException extends RegraVioladaException {
    public PautaJaPossuiSessaoException() {
        super(PAUTA_JA_POSSUI_SESSAO);
    }
}