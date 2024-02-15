package io.github.brunorsch.sicredi.sessao.votacao.service;

import static io.github.brunorsch.sicredi.sessao.votacao.utils.MascaramentoUtils.mascararCpf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.request.CadastrarAssociadoRequest;
import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.AssociadoResponse;
import io.github.brunorsch.sicredi.sessao.votacao.data.repository.AssociadoRepository;
import io.github.brunorsch.sicredi.sessao.votacao.domain.Associado;
import io.github.brunorsch.sicredi.sessao.votacao.exception.AssociadoJaCadastradoException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.AssociadoNaoEncontradoException;
import io.github.brunorsch.sicredi.sessao.votacao.exception.AssociadoNaoEstaAptoException;
import io.github.brunorsch.sicredi.sessao.votacao.integration.WhitelistIntegration;
import io.github.brunorsch.sicredi.sessao.votacao.mapper.AssociadoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrudAssociadoService {
    private final AssociadoRepository associadoRepository;
    private final AssociadoMapper mapper;
    private final WhitelistIntegration whitelistIntegration;

    @Value("${app.whitelist.ativado}")
    private boolean isWhitelistAtiva;

    public Associado buscar(final Long id) {
        log.debug("Buscando associado com ID: {}", id);

        return associadoRepository.findById(id)
            .orElseThrow(AssociadoNaoEncontradoException::new);
    }

    public AssociadoResponse cadastrar(final CadastrarAssociadoRequest request) {
        log.info("Cadastrando associado: {}", mascararCpf(request.getCpf()));

        if(isWhitelistAtiva && !whitelistIntegration.isCpfApto(request.getCpf())) {
            log.warn("Associado não está apto para votar");
            throw new AssociadoNaoEstaAptoException();
        }

        if (associadoRepository.existsByCpf(request.getCpf())) {
            log.warn("Associado já cadastrado");
            throw new AssociadoJaCadastradoException();
        }

        final Associado associado = new Associado();
        associado.setCpf(request.getCpf());

        return mapper.toAssociadoResponse(associadoRepository.save(associado));
    }
}