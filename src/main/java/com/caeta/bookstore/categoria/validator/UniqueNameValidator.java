package com.caeta.bookstore.categoria.validator;

import com.caeta.bookstore.categoria.Categoria;
import com.caeta.bookstore.categoria.CategoriaRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public boolean isValid(String nome, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Categoria> categoria = categoriaRepository.findByNome(nome);

        if (categoria.isEmpty()) {
            return true;
        } else {
            Assert.state(categoria.isPresent(), "Foi encontrado mais de um nome");
            return false;
        }
    }
}
