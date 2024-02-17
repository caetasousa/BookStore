package com.caeta.bookstore.estado;

import com.caeta.bookstore.estado.validators.ExistsPais;
import com.caeta.bookstore.estado.validators.UniqueEstado;
import com.caeta.bookstore.pais.Pais;
import com.caeta.bookstore.pais.PaisRepository;
import jakarta.validation.constraints.NotBlank;

import java.util.NoSuchElementException;

public class EstadoRequest {

    @NotBlank
    @UniqueEstado
    private String nome;

    @ExistsPais
    private Long id_pais;

    public EstadoRequest(String nome, Long id_pais) {
        this.nome = nome;
        this.id_pais = id_pais;
    }

    public Estado toModel(PaisRepository paisRepository){
        if (this.id_pais == null) {
            throw new IllegalArgumentException("O ID do país não pode ser nulo");
        }

        Pais pais = paisRepository.findById(id_pais)
                .orElseThrow(() -> new NoSuchElementException("Pais não encontrado"));
        return new Estado(this.nome, pais);
    }

    public String getNome() {
        return nome;
    }

    public Long getId_pais() {
        return id_pais;
    }
}
