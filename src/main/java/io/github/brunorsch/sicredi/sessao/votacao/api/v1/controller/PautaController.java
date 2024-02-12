package io.github.brunorsch.sicredi.sessao.votacao.api.v1.controller;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CriarPautaRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.PautaResponse;
import io.github.brunorsch.sicredi.sessao.votacao.service.CrudPautaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/pautas")
@RequiredArgsConstructor
public class PautaController implements PautaApi {
    private final CrudPautaService service;

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public PautaResponse post(@Validated @RequestBody final CriarPautaRequest request) {
        return service.criar(request);
    }
}