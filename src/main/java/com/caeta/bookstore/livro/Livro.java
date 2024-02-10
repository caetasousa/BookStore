package com.caeta.bookstore.livro;

import com.caeta.bookstore.autor.Autor;
import com.caeta.bookstore.categoria.Categoria;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank()
    private String titulo;

    @NotBlank()
    @Size(max = 500)
    private String resumo;

    @Column(columnDefinition = "TEXT")
    private String sumario;

    @NotNull()
    @DecimalMin(value = "20")
    private BigDecimal preco;

    @NotNull()
    @Min(value = 100)
    private Integer numeroPaginas;

    @NotBlank()
    @Column(unique = true)
    private String isbn;

    @Future()
    private LocalDate dataPublicacao;

    @ManyToOne
    private Autor autor;

    @ManyToOne
    private Categoria categoria;

    public Livro() {
    }

    public Livro(String titulo,
                 String resumo,
                 String sumario,
                 BigDecimal preco,
                 Integer numeroPaginas,
                 String isbn,
                 LocalDate dataPublicacao,
                 Autor autor,
                 Categoria categoria) {

        this.titulo = titulo;
        this.resumo = resumo;
        this.sumario = sumario;
        this.preco = preco;
        this.numeroPaginas = numeroPaginas;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.autor = autor;
        this.categoria = categoria;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public Autor getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", resumo='" + resumo + '\'' +
                ", sumario='" + sumario + '\'' +
                ", preco=" + preco +
                ", numeroPaginas=" + numeroPaginas +
                ", isbn='" + isbn + '\'' +
                ", dataPublicacao=" + dataPublicacao +
                ", categoriaId=" + categoria.toString() +
                ", autorId=" + autor.toString() +
                '}';
    }
}