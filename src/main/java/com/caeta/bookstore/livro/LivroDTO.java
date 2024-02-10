package com.caeta.bookstore.livro;

public class LivroDTO {

    private Long id;
    private String titulo;

    public LivroDTO(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public LivroDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
