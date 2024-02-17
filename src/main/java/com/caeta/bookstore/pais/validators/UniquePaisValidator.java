package com.caeta.bookstore.pais.validators;

import com.caeta.bookstore.pais.Pais;
import com.caeta.bookstore.pais.PaisRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;

public class UniquePaisValidator implements ConstraintValidator<UniquePais, String> {

    @Autowired
    PaisRepository paisRepository;

    @Override
    public boolean isValid(String nome, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Pais> pais = paisRepository.findByNome(nome);

        if (pais.isEmpty()) {
            return true;
        }else {
            Assert.state(pais.isPresent(), "Foi encontrado mais de um Livro");
            return false;
        }
    }
}
