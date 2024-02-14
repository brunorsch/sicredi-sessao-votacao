package io.github.brunorsch.sicredi.sessao.votacao.api.v1.controller;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CadastrarAssociadoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.AssociadoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Associados", description = "Cadastro de associados")
public interface AssociadoApi {
    @Operation(summary = "Cadastrar associado", description = "Cadastra um novo associado na base de dados")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Associado cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    AssociadoResponse post(@RequestBody CadastrarAssociadoRequest request);
}