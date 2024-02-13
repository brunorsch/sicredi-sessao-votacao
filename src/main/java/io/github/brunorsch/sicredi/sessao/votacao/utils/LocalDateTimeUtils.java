package io.github.brunorsch.sicredi.sessao.votacao.utils;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.ObjectUtils.anyNull;

import java.time.LocalDateTime;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class LocalDateTimeUtils {
    public static boolean isFuturoOuPresente(final LocalDateTime dataInicial, final LocalDateTime dataFinal) {
        if(anyNull(dataInicial, dataFinal)) {
            return false;
        }

        return dataInicial.isBefore(dataFinal) || dataInicial.isEqual(dataFinal);
    }
}