package io.github.brunorsch.sicredi.sessao.votacao.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MascaramentoUtilsTest {
    @Test
    void deveRetornarNullQuandoParametroNulo() {
        assertNull(MascaramentoUtils.mascararCpf(null));
    }

    @Test
    void deveNaoAplicarMascaraQuandoParametroNaoPossuirTamanhoCpf() {
        String cpf = "123";
        assertEquals(cpf, MascaramentoUtils.mascararCpf(cpf));
    }

    @Test
    void deveMascararCorretamente() {
        String cpf = "12345678901";
        String cpfMascarado = MascaramentoUtils.mascararCpf(cpf);
        assertEquals("***456789**", cpfMascarado);
    }
}