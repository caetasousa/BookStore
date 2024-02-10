package com.caeta.bookstore.livro;

import com.caeta.bookstore.autor.AutorRepository;
import com.caeta.bookstore.categoria.CategoriaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livro")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AutorRepository autorRepository;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public String createAutor(@RequestBody @Valid LivroRequest request) {
        Livro livro = request.toModel(categoriaRepository, autorRepository);
        livroRepository.save(livro);
        return livro.toString();
    }

    @GetMapping
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public List<LivroDTO> listarLivros() {
        List<Object[]> livrosData = livroRepository.findAllLivros();

        return livrosData.stream()
                .map(data -> new LivroDTO((Long) data[0], (String) data[1]))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroConsultaDTO> consultarLivro(@PathVariable Long id) {
        return livroRepository.findById(id)
                .map(livro -> ResponseEntity.ok(new LivroConsultaDTO(livro)))
                .orElse(ResponseEntity.notFound().build());
    }
}
