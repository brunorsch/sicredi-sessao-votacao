package io.github.brunorsch.sicredi.sessao.votacao.testutils;

import static java.util.Arrays.stream;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.RandomUtils.nextLong;

import java.util.Arrays;
import java.util.stream.Stream;

import org.jeasy.random.EasyRandom;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Random {
    private static final int[] PESOS_CPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    public static <T> T obj(Class<T> clazz) {
        EASY_RANDOM.setSeed(nextLong());
        return EASY_RANDOM.nextObject(clazz);
    }

    public static String cpf() {
        final var baseNumerica = Stream.generate(() -> nextInt(0, 10))
            .limit(9)
            .mapToInt(Integer::intValue)
            .toArray();

        final var digitosCpf = calcularDigitoVerificador(baseNumerica);

        return stream(digitosCpf)
            .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
            .toString();
    }

    private static int[] calcularDigitoVerificador(final int[] baseNumerica) {
        var isCalculandoPrimeiroDigito = baseNumerica.length <= 9;
        var somaDigitos = 0;

        for (var i = 0; i < baseNumerica.length; i++) {
            var indicePeso = isCalculandoPrimeiroDigito ? i + 1 : i;
            somaDigitos += baseNumerica[i] * PESOS_CPF[indicePeso];
        }

        final var resto = somaDigitos % 11;

        var digito = 0;

        if (!(resto < 2)) {
            digito = 11 - resto;
        }

        final var novaBase = Arrays.copyOf(baseNumerica, baseNumerica.length + 1);
        novaBase[baseNumerica.length] = digito;

        if (novaBase.length < 11) {
            return calcularDigitoVerificador(novaBase);
        } else {
            return novaBase;
        }
    }
}