package com.caeta.bookstore.pais;

import com.caeta.bookstore.pais.validators.UniquePais;
import jakarta.validation.constraints.NotBlank;

public class PaisRequest {

    @NotBlank
    @UniquePais
    private String nome;

    public PaisRequest() {
    }

    public PaisRequest(@NotBlank String nome) {
        this.nome = nome;
    }

    public Pais toModel(){
        return new Pais(this.nome);
    }

    public String getNome() {
        return nome;
    }
}
