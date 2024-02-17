package com.caeta.bookstore.pais;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pais")
public class PaisController {

    @Autowired
    PaisRepository repository;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public String createPais(@RequestBody @Valid PaisRequest request){
        Pais pais = request.toModel();
        repository.save(pais);

        return pais.toString();
    }
}
