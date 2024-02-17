package com.caeta.bookstore.estado.validators;

import com.caeta.bookstore.estado.Estado;
import com.caeta.bookstore.estado.EstadoRepository;
import com.caeta.bookstore.pais.Pais;
import com.caeta.bookstore.pais.PaisRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;

public class UniqueEstadoValidator implements ConstraintValidator<UniqueEstado, String> {

    @Autowired
    EstadoRepository estadoRepository;

    @Override
    public boolean isValid(String nome, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Estado> estado = estadoRepository.findByNome(nome);

        if (estado.isEmpty()) {
            return true;
        }else {
            Assert.state(estado.isPresent(), "Foi encontrado mais de um Livro");
            return false;
        }
    }
}
