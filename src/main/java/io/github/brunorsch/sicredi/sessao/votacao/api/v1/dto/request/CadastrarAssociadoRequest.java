package io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request;

import org.hibernate.validator.constraints.br.CPF;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CadastrarAssociadoRequest {
    @Schema(description = "CPF do associado")
    @NotNull(message = "{erros.cpf-invalido}")
    @CPF(message = "{erros.cpf-invalido}")
    private String cpf;
}