package io.github.brunorsch.sicredi.sessao.votacao.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultadoVotacao {
    private Long idPauta;
    private Long votosSim;
    private Long votosNao;
}