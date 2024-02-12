package io.github.brunorsch.sicredi.sessao.votacao.exception;

import static io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro.DATA_HORA_DEVE_SER_FUTURO;

public class DataHoraDeveSerFuturoException extends RegraVioladaException {
    public DataHoraDeveSerFuturoException() {
        super(DATA_HORA_DEVE_SER_FUTURO);
    }
}