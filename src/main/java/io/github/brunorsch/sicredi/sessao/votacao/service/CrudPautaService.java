package io.github.brunorsch.sicredi.sessao.votacao.service;

import org.springframework.stereotype.Service;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CriarPautaRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.PautaResponse;
import io.github.brunorsch.sicredi.sessao.votacao.mapper.PautaMapper;
import io.github.brunorsch.sicredi.sessao.votacao.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrudPautaService {
    private final PautaMapper mapper;
    private final PautaRepository repository;

    public PautaResponse criar(final CriarPautaRequest request) {
        log.info("Criando nova pauta: {}", request);

        var pautaMapeada = mapper.fromCriarPautaRequest(request);
        var pautaSalva = repository.save(pautaMapeada);

        return mapper.toPautaResponse(pautaSalva);
    }
}