package io.github.brunorsch.sicredi.sessao.votacao.utils;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class MascaramentoUtils {
    private static final int TAMANHO_CPF = 11;
    private static final String MASCARA_CPF = "***%s**";

    public static String mascararCpf(final String cpf) {
        if (isNull(cpf) || cpf.length() != TAMANHO_CPF) {
            return cpf;
        }
        return MASCARA_CPF.formatted(cpf.substring(3, 9));
    }
}