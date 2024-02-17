package com.caeta.bookstore.estado;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoRepository extends CrudRepository<Estado, Long> {

    Optional<Estado> findByNome(String nome);
}
