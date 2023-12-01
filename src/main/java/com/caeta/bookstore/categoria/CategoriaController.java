package com.caeta.bookstore.categoria;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/categoria")
public class CategoriaController {

    @Autowired
    CategoriaRepository repository;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public String createCategoria(@RequestBody @Valid CategoriaRequest request){
        Categoria categoria = request.toModel();
        repository.save(categoria);

        return categoria.toString();
    }
}
