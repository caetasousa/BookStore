package com.caeta.bookstore.livro.validators;

import com.caeta.bookstore.autor.Autor;
import com.caeta.bookstore.autor.AutorRepository;
import com.caeta.bookstore.livro.Livro;
import com.caeta.bookstore.livro.LivroRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;

public class ExistsAutorValidate implements ConstraintValidator<ExistsAutor, Long> {

    @Autowired
    AutorRepository autorRepository;

    @Override
    public boolean isValid(Long autor_id, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Autor> autor = autorRepository.findById(autor_id);

        if (autor.isPresent()) {
            return true;
        }else {
            Assert.state(autor.isEmpty(), "Autor não encontrado");
            return false;
        }
    }
}
