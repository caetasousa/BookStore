package com.caeta.bookstore.livro;

import com.caeta.bookstore.autor.Autor;
import com.caeta.bookstore.autor.AutorRepository;
import com.caeta.bookstore.categoria.Categoria;
import com.caeta.bookstore.categoria.CategoriaRepository;
import com.caeta.bookstore.livro.validators.ExistsAutor;
import com.caeta.bookstore.livro.validators.ExistsCategoria;
import com.caeta.bookstore.livro.validators.UniqueIsbn;
import com.caeta.bookstore.livro.validators.UniqueTitulo;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

public class LivroRequest {

    @NotBlank()
    @UniqueTitulo()
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
    @UniqueIsbn()
    private String isbn;

    @Future()
    private LocalDate dataPublicacao;

    @NotNull()
    @ExistsCategoria()
    @Column(name = "categoria_id")
    private Long categoriaId;

    @NotNull()
    @ExistsAutor()
    @Column(name = "autor_id")
    private Long autorId;

    public LivroRequest() {
    }

    public LivroRequest(@NotBlank() @UniqueTitulo() String titulo,
                        @NotBlank() @Size(max = 500) String resumo,
                        String sumario,
                        @NotNull() @DecimalMin(value = "20") BigDecimal preco,
                        @NotNull() @Min(value = 100) Integer numeroPaginas,
                        @NotBlank() @UniqueIsbn() String isbn,
                        @Future() LocalDate dataPublicacao,
                        @NotNull() @ExistsCategoria Long categoriaId,
                        @NotNull() @ExistsAutor() Long autorId) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.sumario = sumario;
        this.preco = preco;
        this.numeroPaginas = numeroPaginas;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.categoriaId = categoriaId;
        this.autorId = autorId;
    }

    public Livro toModel(CategoriaRepository categoriaRepository, AutorRepository autorRepository) {
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new NoSuchElementException("Autor não encontrado"));

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new NoSuchElementException("Categoria não encontrada"));

        return new Livro(
                this.titulo,
                this.resumo,
                this.sumario,
                this.preco,
                this.numeroPaginas,
                this.isbn,
                this.dataPublicacao,
                autor,
                categoria
        );
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    @Override
    public String toString() {
        return "LivroRequest{" +
                "titulo='" + titulo + '\'' +
                ", resumo='" + resumo + '\'' +
                ", sumario='" + sumario + '\'' +
                ", preco=" + preco +
                ", numeroPaginas=" + numeroPaginas +
                ", isbn='" + isbn + '\'' +
                ", dataPublicacao=" + dataPublicacao +
                ", categoriaId=" + categoriaId +
                ", autorId=" + autorId +
                '}';
    }
}
