package com.caeta.bookstore.livro;

import com.caeta.bookstore.autor.Autor;
import com.caeta.bookstore.categoria.Categoria;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class LivroConsultaDTO {

    private Long id;

    private String titulo;

    private String resumo;

    private String sumario;

    private BigDecimal preco;

    private Integer numeroPaginas;

    private String isbn;

    private LocalDate dataPublicacao;

    private Autor autor;

    public LivroConsultaDTO(Livro consulta){
        this.id = consulta.getId();
        this.titulo = consulta.getTitulo();
        this.resumo = consulta.getResumo();
        this.sumario = consulta.getSumario();
        this.preco = consulta.getPreco();
        this.numeroPaginas = consulta.getNumeroPaginas();
        this.isbn = consulta.getIsbn();
        this.dataPublicacao = consulta.getDataPublicacao();
        this.autor = consulta.getAutor();
    }

    public LivroConsultaDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public String getSumario() {
        return sumario;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public Autor getAutor() {
        return autor;
    }
}
