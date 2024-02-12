package io.github.brunorsch.sicredi.sessao.votacao.validator;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.springframework.stereotype.Component;

import io.github.brunorsch.sicredi.sessao.votacao.annotations.NuloOuNotBlank;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class NuloOuNotBlankValidator implements ConstraintValidator<NuloOuNotBlank, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return isNull(value) || isNotBlank(value);
    }
}