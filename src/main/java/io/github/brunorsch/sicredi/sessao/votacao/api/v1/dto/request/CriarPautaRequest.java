package io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request;

import io.github.brunorsch.sicredi.sessao.votacao.annotations.NuloOuNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CriarPautaRequest {
    @Schema(description = "Título da pauta", example = "Pauta de teste")
    @NotBlank(message = "{erros.criar-pauta.titulo-obrigatorio}")
    @Size(max = 64, message = "{erros.criar-pauta.titulo-tamanho-maximo}")
    private String titulo;

    @Schema(description = "Descrição da pauta", example = "Descrição da pauta de teste", nullable = true)
    @NuloOuNotBlank(message = "{erros.criar-pauta.descricao-nao-pode-ser-vazia}")
    @Size(max = 2000, message = "{erros.criar-pauta.descricao-tamanho-maximo}")
    private String descricao;
}