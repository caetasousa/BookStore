package com.caeta.bookstore.pais.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = UniquePaisValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePais {

    String message() default "Esse pais ja existe";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
