package io.github.brunorsch.sicredi.sessao.votacao.domain;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class PautaTest {
    @Test
    void isSessaoIniciadaDeveRetornarTrueQuandoDataHoraFimVotacaoForNaoNula() {
        final var pauta = new Pauta();
        pauta.setDataHoraFimVotacao(LocalDateTime.now());

        assertTrue(pauta.isSessaoIniciada());
    }

    @Test
    void isSessaoIniciadaDeveRetornarFalseQuandoDataHoraFimVotacaoForNula() {
        final var pauta = new Pauta();
        pauta.setDataHoraFimVotacao(null);

        assertFalse(pauta.isSessaoIniciada());
    }

    @Test
    void isSessaoEmAndamentoDeveRetornarTrueQuandoDataHoraFimVotacaoForMaiorQueAtual() {
        final var pauta = new Pauta();
        pauta.setDataHoraFimVotacao(LocalDateTime.now().plusMinutes(1));

        assertTrue(pauta.isSessaoEmAndamento(LocalDateTime.now()));
    }

    @Test
    void isSessaoEmAndamentoDeveRetornarTrueQuandoDataHoraFimVotacaoForIgualAAtual() {
        final var pauta = new Pauta();
        final var dataHoraAtual = LocalDateTime.of(2024, 2, 14, 0, 0, 0, 0);
        pauta.setDataHoraFimVotacao(dataHoraAtual);

        assertTrue(pauta.isSessaoEmAndamento(dataHoraAtual));
    }

    @Test
    void isSessaoEmAndamentoDeveRetornarFalseQuandoDataHoraFimVotacaoForMenorQueAtual() {
        final var pauta = new Pauta();
        pauta.setDataHoraFimVotacao(LocalDateTime.now().minusMinutes(1));

        assertFalse(pauta.isSessaoEmAndamento(LocalDateTime.now()));
    }

    @Test
    void isSessaoEmAndamentoDeveRetornarFalseQuandoDataHoraFimVotacaoForNula() {
        final var pauta = new Pauta();
        pauta.setDataHoraFimVotacao(null);

        assertFalse(pauta.isSessaoEmAndamento(LocalDateTime.now()));
    }
}