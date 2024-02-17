package com.caeta.bookstore.estado.validators;

import com.caeta.bookstore.pais.Pais;
import com.caeta.bookstore.pais.PaisRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;

public class ExistsPaisValidate implements ConstraintValidator<ExistsPais, Long> {

    @Autowired
    PaisRepository paisRepository;

    @Override
    public boolean isValid(Long id_pais, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Pais> pais = paisRepository.findById(id_pais);

        if (pais.isPresent()) {
            return true;
        }else {
            Assert.state(pais.isEmpty(), "Autor não encontrado");
            return false;
        }
    }
}
