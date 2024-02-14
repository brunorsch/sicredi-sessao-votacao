package io.github.brunorsch.sicredi.sessao.votacao.support;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CodigoErro {
    APURACAO_JA_REALIZADA(UNPROCESSABLE_ENTITY, "erros.processar-votos.apuracao-ja-realizada"),
    ASSOCIADO_JA_CADASTRADO(UNPROCESSABLE_ENTITY, "erros.cadastrar-associado.associado-ja-cadastrado"),
    ASSOCIADO_NAO_ENCONTRADO(UNPROCESSABLE_ENTITY, "erros.associado-nao-encontrado"),
    DATA_HORA_DEVE_SER_FUTURO(BAD_REQUEST, "erros.data-hora-deve-ser-futuro"),
    PAUTA_JA_POSSUI_SESSAO(UNPROCESSABLE_ENTITY, "erros.abrir-sessao.pauta-ja-possui-sessao"),
    PAUTA_NAO_ENCONTRADA(NOT_FOUND, "erros.pauta-nao-encontrada"),
    SESSAO_JA_ENCERRADA(UNPROCESSABLE_ENTITY, "erros.registrar-voto.sessao-ja-encerrada"),
    SESSAO_AINDA_EM_ANDAMENTO(UNPROCESSABLE_ENTITY, "erros.processar-votos.sessao-ainda-em-andamento"),
    SESSAO_NAO_ABERTA(PRECONDITION_FAILED, "erros.registrar-voto.sessao-nao-aberta"),
    VALIDACAO(BAD_REQUEST, null),
    VOTO_JA_REALIZADO(UNPROCESSABLE_ENTITY, "erros.registrar-voto.voto-ja-realizado");

    private final HttpStatus httpStatus;
    private final String mensagem;
}