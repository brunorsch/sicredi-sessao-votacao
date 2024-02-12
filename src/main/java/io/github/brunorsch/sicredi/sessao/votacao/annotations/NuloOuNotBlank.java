package io.github.brunorsch.sicredi.sessao.votacao.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.github.brunorsch.sicredi.sessao.votacao.validator.NuloOuNotBlankValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = NuloOuNotBlankValidator.class)
public @interface NuloOuNotBlank {
    String message() default "{javax.validation.constraints.NullOrNotBlank.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
}