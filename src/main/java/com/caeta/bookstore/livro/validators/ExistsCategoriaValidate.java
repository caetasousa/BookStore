package com.caeta.bookstore.livro.validators;

import com.caeta.bookstore.categoria.Categoria;
import com.caeta.bookstore.categoria.CategoriaRepository;
import com.caeta.bookstore.livro.Livro;
import com.caeta.bookstore.livro.LivroRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;

public class ExistsCategoriaValidate implements ConstraintValidator<ExistsCategoria, Long> {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public boolean isValid(Long categoria_id, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Categoria> categoria = categoriaRepository.findById(categoria_id);

        if (categoria.isPresent()) {
            return true;
        }else {
            Assert.state(categoria.isEmpty(), "A categoria não existe");
            return false;
        }
    }
}
