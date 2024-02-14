package io.github.brunorsch.sicredi.sessao.votacao.service;

import static io.github.brunorsch.sicredi.sessao.votacao.utils.MascaramentoUtils.mascararCpf;

import org.springframework.stereotype.Service;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CadastrarAssociadoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.AssociadoResponse;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Associado;
import io.github.brunorsch.sicredi.sessao.votacao.exception.AssociadoNaoEncontradoException;
import io.github.brunorsch.sicredi.sessao.votacao.mapper.AssociadoMapper;
import io.github.brunorsch.sicredi.sessao.votacao.repository.AssociadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrudAssociadoService {
    private final AssociadoRepository associadoRepository;
    private final AssociadoMapper mapper;

    public Associado buscar(final Long id) {
        return associadoRepository.findById(id)
            .orElseThrow(AssociadoNaoEncontradoException::new);
    }

    public AssociadoResponse cadastrar(final CadastrarAssociadoRequest request) {
        log.info("Cadastrando associado: {}", mascararCpf(request.getCpf()));

        final Associado associado = new Associado();
        associado.setCpf(request.getCpf());

        return mapper.toAssociadoResponse(associadoRepository.save(associado));
    }
}