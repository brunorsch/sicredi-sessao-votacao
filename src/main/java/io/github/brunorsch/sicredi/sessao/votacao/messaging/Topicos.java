package io.github.brunorsch.sicredi.sessao.votacao.messaging;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Topicos {
    RESULTADO_VOTACAO("resultado-votacao");

    private final String nome;
}