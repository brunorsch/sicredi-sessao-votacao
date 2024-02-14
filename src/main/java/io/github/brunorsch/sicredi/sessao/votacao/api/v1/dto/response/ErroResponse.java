package io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response;

import io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErroResponse {
    private CodigoErro codigo;
    private String mensagem;
}