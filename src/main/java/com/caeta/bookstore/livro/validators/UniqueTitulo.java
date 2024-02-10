package com.caeta.bookstore.livro.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = UniqueTituloValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueTitulo{

    String message() default "Esse titulo ja existe";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
