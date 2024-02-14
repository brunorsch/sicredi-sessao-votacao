package io.github.brunorsch.sicredi.sessao.votacao.utils;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class LocalDateTimeUtils {
    public static boolean isAntesOuIgual(final LocalDateTime dataInicial, final LocalDateTime dataFinal) {
        return dataInicial.isBefore(dataFinal) || dataInicial.isEqual(dataFinal);
    }
}