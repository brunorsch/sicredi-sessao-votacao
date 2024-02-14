package io.github.brunorsch.sicredi.sessao.votacao.messaging.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultadoVotacaoPayload {
    private Long idPauta;
    private Long votosSim;
    private Long votosNao;
}