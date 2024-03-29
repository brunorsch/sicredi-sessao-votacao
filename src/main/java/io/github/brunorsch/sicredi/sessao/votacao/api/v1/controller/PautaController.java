package io.github.brunorsch.sicredi.sessao.votacao.api.v1.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.AbrirSessaoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CriarPautaRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.VotoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.PautaResponse;
import io.github.brunorsch.sicredi.sessao.votacao.service.CrudPautaService;
import io.github.brunorsch.sicredi.sessao.votacao.service.SessaoVotacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/pautas")
@RequiredArgsConstructor
public class PautaController implements PautaApi {
    private final CrudPautaService crudService;
    private final SessaoVotacaoService sessaoVotacaoService;

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public PautaResponse post(@Valid @RequestBody final CriarPautaRequest request) {
        return crudService.criar(request);
    }

    @Override
    @PostMapping("/{id}/sessao-votacao")
    @ResponseStatus(CREATED)
    public void postVotacao(@PathVariable("id") final Long id, @Valid @RequestBody final AbrirSessaoRequest request) {
        sessaoVotacaoService.abrir(id, request.getDataHoraFimVotacao());
    }

    @Override
    @PostMapping("/{id}/voto")
    @ResponseStatus(CREATED)
    public void postVoto(@PathVariable("id") final Long id, @Valid @RequestBody final VotoRequest request) {
        sessaoVotacaoService.registrarVoto(id, request);
    }

    @Override
    @GetMapping
    @ResponseStatus(OK)
    public Page<PautaResponse> get(@PageableDefault final Pageable pageable) {
        return crudService.listar(pageable);
    }
}