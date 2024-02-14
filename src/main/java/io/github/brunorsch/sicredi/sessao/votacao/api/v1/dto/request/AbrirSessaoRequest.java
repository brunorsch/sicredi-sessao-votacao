package io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AbrirSessaoRequest {
    @Schema(
        description = "Data e hora do fechamento da sessão. " +
            "Caso não seja informado, será usado a data e hora atuais + 1 minuto.",
        example = "2024-02-01T20:00:00",
        nullable = true)
    private LocalDateTime dataHoraFimVotacao;
}