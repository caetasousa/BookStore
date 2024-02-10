package com.caeta.bookstore.livro.validators;

import com.caeta.bookstore.livro.Livro;
import com.caeta.bookstore.livro.LivroRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;

public class UniqueTituloValidator implements ConstraintValidator<UniqueTitulo, String> {

    @Autowired
    LivroRepository livroRepository;

    @Override
    public boolean isValid(String titulo, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Livro> livro = livroRepository.findByTitulo(titulo);

        if (livro.isEmpty()) {
            return true;
        }else {
            Assert.state(livro.isPresent(), "Foi encontrado mais de um Livro");
            return false;
        }
    }
}
