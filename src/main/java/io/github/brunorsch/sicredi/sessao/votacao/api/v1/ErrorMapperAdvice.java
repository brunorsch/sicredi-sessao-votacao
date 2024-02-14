package io.github.brunorsch.sicredi.sessao.votacao.api.v1;

import static io.github.brunorsch.sicredi.sessao.votacao.support.CodigoErro.VALIDACAO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.github.brunorsch.sicredi.sessao.votacao.api.v1.dto.response.ErroResponse;
import io.github.brunorsch.sicredi.sessao.votacao.exception.RegraVioladaException;
import io.github.brunorsch.sicredi.sessao.votacao.service.I18nService;
import lombok.RequiredArgsConstructor;

@ControllerAdvice(basePackages = "io.github.brunorsch.sicredi.sessao.votacao.api.v1")
@RequiredArgsConstructor
public class ErrorMapperAdvice {
    private final I18nService i18nService;

    @ExceptionHandler(value = { RegraVioladaException.class })
    protected ResponseEntity<ErroResponse> mapearRegraViolada(final RegraVioladaException exception) {
        final var body = ErroResponse.builder()
            .codigo(exception.getCodigoErro())
            .mensagem(i18nService.get(exception.getCodigoErro().getMensagem()))
            .build();

        return new ResponseEntity<>(body, exception.getCodigoErro().getHttpStatus());
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected ResponseEntity<ErroResponse> mapearConstraintViolation(final MethodArgumentNotValidException exception) {
        final var mensagemPrimeiroErro = exception.getBindingResult().getFieldErrors().get(0)
            .getDefaultMessage();

        final var codigoErro = VALIDACAO;
        final var body = ErroResponse.builder()
            .codigo(codigoErro)
            .mensagem(i18nService.get(mensagemPrimeiroErro))
            .build();

        return new ResponseEntity<>(body, codigoErro.getHttpStatus());
    }
}