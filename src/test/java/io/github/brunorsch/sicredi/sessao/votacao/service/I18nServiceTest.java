package io.github.brunorsch.sicredi.sessao.votacao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

@ExtendWith(MockitoExtension.class)
class I18nServiceTest {
    @InjectMocks
    private I18nService service;

    @Mock
    private MessageSource messageSource;

    @Test
    void getDeveRetornarMensagemCorretamenteSemParametros() {
        var esperado = "Test";
        when(messageSource.getMessage("test", new Object[]{}, Locale.getDefault()))
            .thenReturn(esperado);

        var resultado = service.get("test");

        assertEquals(esperado, resultado);
    }

    @Test
    void getDeveRetornarMensagemCorretamenteComParametros() {
        var esperado = "Test 1";
        when(messageSource.getMessage("test", new Object[]{"1"}, Locale.getDefault()))
            .thenReturn(esperado);

        var resultado = service.get("test", "1");

        assertEquals(esperado, resultado);
    }
}