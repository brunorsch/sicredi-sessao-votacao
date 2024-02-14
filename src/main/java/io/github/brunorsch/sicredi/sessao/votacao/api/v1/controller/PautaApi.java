package io.github.brunorsch.sicredi.sessao.votacao.api.v1.controller;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.AbrirSessaoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CriarPautaRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.VotoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.ErroResponse;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.PautaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pautas", description = "Gestão das pautas de votação")
public interface PautaApi {
    @Operation(summary = "Criar pauta", description = "Cria uma nova pauta de votação")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida",
            content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    PautaResponse post(@RequestBody CriarPautaRequest request);

    @Operation(summary = "Abrir sessão de votação para a pauta")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Sessão aberta com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida",
            content = @Content(schema = @Schema(implementation = ErroResponse.class))),
        @ApiResponse(responseCode = "404", description = "Pauta não encontrada",
            content = @Content(schema = @Schema(implementation = ErroResponse.class))),
        @ApiResponse(responseCode = "422", description = "Pauta já possui sessão de votação aberta",
            content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    void postVotacao(
        @Parameter(description = "ID da Pauta") Long id,
        @RequestBody AbrirSessaoRequest request);

    @Operation(summary = "Registrar novo voto")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida",
            content = @Content(schema = @Schema(implementation = ErroResponse.class))),
        @ApiResponse(responseCode = "404", description = "Pauta não encontrada",
            content = @Content(schema = @Schema(implementation = ErroResponse.class))),
        @ApiResponse(responseCode = "412", description = "Sessão de votação da pauta não está aberta",
            content = @Content(schema = @Schema(implementation = ErroResponse.class))),
        @ApiResponse(responseCode = "422", description = "Sessão de votação da pauta já foi encerrada",
            content = @Content(schema = @Schema(implementation = ErroResponse.class))),
        @ApiResponse(responseCode = "422", description = "Voto nessa pauta já foi realizado",
            content = @Content(schema = @Schema(implementation = ErroResponse.class)))
    })
    void postVoto(
        @Parameter(description = "ID da Pauta") Long id,
        @RequestBody VotoRequest request);
}