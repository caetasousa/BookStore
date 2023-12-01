package com.caeta.bookstore.categoria;

import com.caeta.bookstore.categoria.validator.UniqueName;
import jakarta.validation.constraints.NotBlank;

public class CategoriaRequest {

    @NotBlank
    @UniqueName
    private String nome;

    public CategoriaRequest() {
    }

    public CategoriaRequest(@UniqueName @NotBlank String nome) {
        this.nome = nome;
    }

    public Categoria toModel(){
        return new Categoria(this.nome);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "CategoriaRequest{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
