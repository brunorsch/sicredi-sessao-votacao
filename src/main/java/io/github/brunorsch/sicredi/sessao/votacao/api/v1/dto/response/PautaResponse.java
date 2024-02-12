package io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PautaResponse {
    private Long id;

    @Schema(description = "Título da pauta", example = "Pauta de teste")
    private String titulo;

    @Schema(description = "Descrição da pauta", example = "Descrição da pauta de teste", nullable = true)
    private String descricao;

    @Schema(
        description = "Data e hora do fim da votação, nulo caso votação não tenha sido iniciada",
        example = "2024-02-01T20:00:00",
        nullable = true
    )
    private LocalDateTime dataHoraFimVotacao;
}