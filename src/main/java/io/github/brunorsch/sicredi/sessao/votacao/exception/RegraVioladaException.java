package io.github.brunorsch.sicredi.sessao.votacao.exception;

import io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro;
import lombok.Getter;

@Getter
public abstract class RegraVioladaException extends RuntimeException {
    private final CodigoErro codigoErro;

    public RegraVioladaException(final CodigoErro codigoErro) {
        super(null, null, true, false);
        this.codigoErro = codigoErro;
    }
}