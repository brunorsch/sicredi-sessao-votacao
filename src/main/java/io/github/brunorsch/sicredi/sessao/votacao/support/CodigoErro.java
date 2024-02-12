package io.github.brunorsch.sicredi.sessao.votacao.support;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CodigoErro {
    DATA_HORA_DEVE_SER_FUTURO(BAD_REQUEST, "erros.data-hora-deve-ser-futuro"),
    PAUTA_JA_POSSUI_SESSAO(UNPROCESSABLE_ENTITY, "erros.abrir-sessao.pauta-ja-possui-sessao"),
    PAUTA_NAO_ENCONTRADA(NOT_FOUND, "erros.pauta-nao-encontrada");

    private final HttpStatus httpStatus;
    private final String mensagem;
}