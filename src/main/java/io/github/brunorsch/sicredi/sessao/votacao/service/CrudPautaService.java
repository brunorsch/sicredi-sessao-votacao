package io.github.brunorsch.sicredi.sessao.votacao.service;

import org.springframework.stereotype.Service;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CriarPautaRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.PautaResponse;
import io.github.brunorsch.sicredi.sessao.votacao.data.repository.PautaRepository;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Pauta;
import io.github.brunorsch.sicredi.sessao.votacao.exception.PautaNaoEncontradaException;
import io.github.brunorsch.sicredi.sessao.votacao.mapper.PautaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrudPautaService {
    private final PautaMapper mapper;
    private final PautaRepository repository;

    public Pauta buscar(final Long id) {
        log.debug("Buscando pauta com ID: {}", id);

        return repository.findById(id)
            .orElseThrow(PautaNaoEncontradaException::new);
    }

    public PautaResponse criar(final CriarPautaRequest request) {
        log.info("Criando nova pauta: {}", request);

        var pautaMapeada = mapper.fromCriarPautaRequest(request);
        var pautaSalva = repository.save(pautaMapeada);

        return mapper.toPautaResponse(pautaSalva);
    }
}