package com.caeta.bookstore.autor;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    @PostMapping
    @Transactional
    public String createAutor(@RequestBody @Valid AutorRequest request) {
        Autor autor = request.toModel();
        autorRepository.save(autor);
        return autor.toString();
    }
}
