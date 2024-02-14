package io.github.brunorsch.sicredi.sessao.votacao.api.v1.controller;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CadastrarAssociadoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.AssociadoResponse;
import io.github.brunorsch.sicredi.sessao.votacao.service.CrudAssociadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/associados")
@RequiredArgsConstructor
public class AssociadoController implements AssociadoApi {
    private final CrudAssociadoService crudAssociadoService;

    @PostMapping
    @ResponseStatus(CREATED)
    public AssociadoResponse post(@Valid @RequestBody final CadastrarAssociadoRequest request) {
        return crudAssociadoService.cadastrar(request);
    }
}