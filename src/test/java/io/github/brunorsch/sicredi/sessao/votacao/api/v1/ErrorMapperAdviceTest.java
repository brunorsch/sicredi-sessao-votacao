package io.github.brunorsch.sicredi.sessao.votacao.api.v1;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import io.github.brunorsch.sicredi.sessao.votacao.exception.ApuracaoJaRealizadaException;
import io.github.brunorsch.sicredi.sessao.votacao.service.I18nService;
import io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro;

@ExtendWith(MockitoExtension.class)
class ErrorMapperAdviceTest {
    @InjectMocks
    private ErrorMapperAdvice errorMapperAdvice;

    @Mock
    private I18nService i18nService;

    @Test
    void mapearRegraVioladaDeveMapearCorretamente() {
        final var mensagem = mockMensagemAleatoria();

        final var entity = errorMapperAdvice.mapearRegraViolada(new ApuracaoJaRealizadaException());

        assertEquals(CodigoErro.APURACAO_JA_REALIZADA.getHttpStatus(), entity.getStatusCode());
        assertEquals(CodigoErro.APURACAO_JA_REALIZADA, entity.getBody().getCodigo());
        assertEquals(mensagem, entity.getBody().getMensagem());
    }

    @Test
    void mapearConstraintViolationDeveMapearCorretamente() {
        final var bindingResult = mock(BindingResult.class);
        final var mensagem = mockMensagemAleatoria();
        when(bindingResult.getFieldErrors())
            .thenReturn(List.of(new FieldError("obj", "field", mensagem)));

        final var entity = errorMapperAdvice.mapearConstraintViolation(
            new MethodArgumentNotValidException(null, bindingResult));

        assertEquals(CodigoErro.VALIDACAO.getHttpStatus(), entity.getStatusCode());
        assertEquals(CodigoErro.VALIDACAO, entity.getBody().getCodigo());
        assertEquals(mensagem, entity.getBody().getMensagem());
    }

    private String mockMensagemAleatoria() {
        final var mensagem = randomAlphabetic(12);

        when(i18nService.get(any()))
            .thenReturn(mensagem);

        return mensagem;
    }
}