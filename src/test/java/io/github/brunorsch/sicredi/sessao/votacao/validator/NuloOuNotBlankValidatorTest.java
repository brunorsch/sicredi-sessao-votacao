package io.github.brunorsch.sicredi.sessao.votacao.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class NuloOuNotBlankValidatorTest {
    private final NuloOuNotBlankValidator nuloOuNotBlankValidator = new NuloOuNotBlankValidator();

    @Test
    void isValidDeveRetornarTrueQuandoValorNulo() {
        assertTrue(nuloOuNotBlankValidator.isValid(null, null));
    }

    @Test
    void isValidDeveRetornarFalseQuandoValorVazio() {
        assertFalse(nuloOuNotBlankValidator.isValid("", null));
    }

    @Test
    void isValidDeveRetornarFalseQuandoValorEmBranco() {
        assertFalse(nuloOuNotBlankValidator.isValid(" ", null));
    }

    @Test
    void isValidDeveRetornarTrueQuandoValorNaoNuloENaoVazio() {
        assertTrue(nuloOuNotBlankValidator.isValid("test", null));
    }
}