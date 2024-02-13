package io.github.brunorsch.sicredi.sessao.votacao.service;

import org.springframework.stereotype.Service;

import io.github.brunorsch.sicredi.sessao.votacao.domain.Associado;
import io.github.brunorsch.sicredi.sessao.votacao.exception.AssociadoNaoEncontradoException;
import io.github.brunorsch.sicredi.sessao.votacao.repository.AssociadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssociadoService {
    private final AssociadoRepository associadoRepository;

    public Associado buscar(final Long id) {
        return associadoRepository.findById(id)
            .orElseThrow(AssociadoNaoEncontradoException::new);
    }
}