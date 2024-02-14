package io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssociadoResponse {
    @Schema(description = "ID do associado na base de dados")
    private Long id;

    @Schema(description = "CPF do associado")
    private String cpf;
}