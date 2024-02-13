package io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request;

import io.github.brunorsch.sicredi.sessao.votacao.domain.Opcao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VotoRequest {
    @Schema(description = "ID do associado", example = "1")
    @NotNull(message = "{erros.id-associado-invalido}")
    private Long idAssociado;

    @Schema(description = "Opção do voto", example = "S")
    @NotNull(message = "{erros.registrar-voto.opcao-obrigatoria}")
    private Opcao opcao;
}