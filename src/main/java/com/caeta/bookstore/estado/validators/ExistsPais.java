package com.caeta.bookstore.estado.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsPaisValidate.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsPais {

    String message() default "Esse pais nao existe";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
