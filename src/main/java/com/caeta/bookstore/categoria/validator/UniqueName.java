package com.caeta.bookstore.categoria.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueNameValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueName {

    String message() default "Esse nome ja existe";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
