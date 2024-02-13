package io.github.brunorsch.sicredi.sessao.votacao.api.v1.controller;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.AbrirSessaoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CriarPautaRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.VotoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.PautaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pautas", description = "Gestão das pautas de votação")
public interface PautaApi {
    @Operation(summary = "Criar pauta", description = "Cria uma nova pauta de votação")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    PautaResponse post(@RequestBody CriarPautaRequest request);

    @Operation(summary = "Abrir sessão de votação para a pauta")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Sessão aberta com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "404", description = "Pauta não encontrada"),
        @ApiResponse(responseCode = "422", description = "Pauta já possui sessão de votação aberta")
    })
    void postVotacao(
        @Parameter(name = "ID da Pauta", example = "1") Long id,
        @RequestBody AbrirSessaoRequest request);

    @Operation(summary = "Registrar novo voto")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "404", description = "Pauta não encontrada"),
        @ApiResponse(responseCode = "412", description = "Sessão de votação da pauta não está aberta")
    })
    void postVoto(
        @Parameter(name = "ID da Pauta", example = "1") Long id,
        @RequestBody VotoRequest request);
}