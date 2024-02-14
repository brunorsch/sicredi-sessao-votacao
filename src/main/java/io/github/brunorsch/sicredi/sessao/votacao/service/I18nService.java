package io.github.brunorsch.sicredi.sessao.votacao.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class I18nService {
    private final MessageSource messageSource;

    public String get(final String codigoMensagem, final String... args) {
        return messageSource.getMessage(codigoMensagem, args, Locale.getDefault());
    }
}