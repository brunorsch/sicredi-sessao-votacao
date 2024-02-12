package io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AbrirSessaoRequest {
    @Schema(
        description = "Data e hora do fechamento da sessão. " +
            "Caso não seja informado, será usado a data e hora atuais + 1 minuto.",
        example = "Descrição da pauta de teste",
        nullable = true)
    private LocalDateTime dataHoraFimVotacao;
}