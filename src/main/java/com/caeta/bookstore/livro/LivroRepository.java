package com.caeta.bookstore.livro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTitulo(String titulo);

    Optional<Livro> findByIsbn(String isbn);

    @Query("SELECT l.id, l.titulo FROM Livro l")
    List<Object[]> findAllLivros();
}
