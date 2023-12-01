package com.caeta.bookstore.autor.validator;

import com.caeta.bookstore.autor.Autor;
import com.caeta.bookstore.autor.AutorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    AutorRepository autorRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Autor> autor = autorRepository.findByEmail(email);

        if (autor.isEmpty()) {
            // Se não há nenhum autor com este e-mail, a validação passa
            return true;
        } else {
            // Se existe um autor, a validação falha se tentar adicionar outro autor com o mesmo e-mail
            Assert.state(autor.isPresent(), "Foi encontrado mais de um email");
            return false;
        }
    }
}
