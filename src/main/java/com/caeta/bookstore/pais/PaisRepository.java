package com.caeta.bookstore.pais;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PaisRepository extends CrudRepository<Pais, Long> {

    Optional<Pais> findByNome(String titulo);
}
