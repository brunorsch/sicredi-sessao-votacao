package io.github.brunorsch.sicredi.sessao.votacao.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class LocalDateTimeUtilsTest {
    @Test
    void isFuturoOuPresenteDeveRetornarTrueQuandoDataForMaiorQueAtual() {
        var dataInicial = LocalDateTime.now();
        var dataFinal = LocalDateTime.now().plusMinutes(1);

        var resultado = LocalDateTimeUtils.isAntesOuIgual(dataInicial, dataFinal);

        assertTrue(resultado);
    }

    @Test
    void isFuturoOuPresenteDeveRetornarTrueQuandoDataForIgualAAtual() {
        var dataInicial = LocalDateTime.now();

        var resultado = LocalDateTimeUtils.isAntesOuIgual(dataInicial, dataInicial);

        assertTrue(resultado);
    }

    @Test
    void isFuturoOuPresenteDeveRetornarFalseQuandoDataForMenorQueAtual() {
        var dataInicial = LocalDateTime.now();
        var dataFinal = LocalDateTime.now().minusMinutes(1);

        var resultado = LocalDateTimeUtils.isAntesOuIgual(dataInicial, dataFinal);

        assertFalse(resultado);
    }
}