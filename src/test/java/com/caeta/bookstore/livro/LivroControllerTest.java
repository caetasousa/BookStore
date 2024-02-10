package com.caeta.bookstore.livro;

import com.caeta.bookstore.autor.Autor;
import com.caeta.bookstore.autor.AutorRepository;
import com.caeta.bookstore.categoria.Categoria;
import com.caeta.bookstore.categoria.CategoriaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest()
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AutorRepository autorRepository;



    @Test
    @Transactional
    void shouldCreateAutor() throws Exception {
        Autor autor = new Autor("Eduardo", "caet@email.com", "Descrição do autor");
        autorRepository.save(autor);

        Categoria categoria = new Categoria("Ficção Fantastica");
        categoriaRepository.save(categoria);

        LivroRequest request = new LivroRequest("Título do Livro", "Resumo do livro... (até 500 caracteres)", "Sumário do livro...",
                BigDecimal.valueOf(29.99) , 200, "978-3-16-148410-7", LocalDate.of(2024,12,31),
                categoria.getId(), autor.getId());

        String jsonRequest = objectMapper.writeValueAsString(request);

        ResultActions resultActions = mockMvc.perform(post("/livro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        var responseStatusCode = resultActions.andReturn().getResponse().getStatus();
        assertEquals(201, responseStatusCode);
    }

    @Test
    @Transactional
    public void shouldNotCreateAnAuthorWithARepeatedTitlIsbnAndInexistentIdAutorIdCategoria() throws Exception {
        Autor autor = new Autor("Eduardo", "caet@email.com", "Descrição do autor");
        autorRepository.save(autor);

        Categoria categoria = new Categoria("Ficção Fantastica");
        categoriaRepository.save(categoria);

        LivroRequest request1 = new LivroRequest("Título do Livro", "Resumo do livro... (até 500 caracteres)", "Sumário do livro...",
                BigDecimal.valueOf(29.99) , 200, "978-3-16-148410-7", LocalDate.of(2024,12,31),
                categoria.getId(), autor.getId());
        livroRepository.save(request1.toModel(categoriaRepository, autorRepository));

        LivroRequest request2 = new LivroRequest("Título do Livro", "Resumo do livro... (até 500 caracteres)", "Sumário do livro...",
                BigDecimal.valueOf(29.99) , 200, "978-3-16-148410-7", LocalDate.of(2024,12,31),
                9999L, 9999L);

        String jsonRequest = objectMapper.writeValueAsString(request2);

        ResultActions resultActions = mockMvc.perform(post("/livro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        var responseBody = resultActions.andReturn().getResponse().getContentAsString();
        var responseStatusCode = resultActions.andReturn().getResponse().getStatus();

        assertTrue(responseBody.contains("Esse isbn ja existe"));
        assertTrue(responseBody.contains("Esse titulo ja existe"));
        assertTrue(responseBody.contains("Esse autor nao existe"));
        assertTrue(responseBody.contains("Essa categoria nao existe"));
        assertEquals(400, responseStatusCode);
    }

    @Test
    @Transactional
    public void shouldReturnIdAndTitulo() throws Exception {
        Autor autor = new Autor("Eduardo", "caet@email.com", "Descrição do autor");
        autorRepository.save(autor);

        Categoria categoria = new Categoria("Ficção Fantastica");
        categoriaRepository.save(categoria);

        LivroRequest request1 = new LivroRequest("Titulo do Livro", "Resumo do livro... (até 500 caracteres)", "Sumário do livro...",
                BigDecimal.valueOf(29.99) , 200, "978-3-16-148410-7", LocalDate.of(2024,12,31),
                categoria.getId(), autor.getId());
        livroRepository.save(request1.toModel(categoriaRepository, autorRepository));

        LivroRequest request2 = new LivroRequest("Titulo do Livro1", "Resumo do livro... (até 500 caracteres)", "Sumário do livro...",
                BigDecimal.valueOf(29.99) , 200, "978-3-16-148410-8", LocalDate.of(2024,12,31),
                categoria.getId(), autor.getId());
        livroRepository.save(request2.toModel(categoriaRepository, autorRepository));

        String responseBody = mockMvc.perform(MockMvcRequestBuilders.get("/livro")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @Transactional
    public void shouldReturnLivroForId() throws Exception {
        Autor autor = new Autor("Eduardo", "caet@email.com", "Descrição do autor");
        autorRepository.save(autor);

        Categoria categoria = new Categoria("Ficção Fantastica");
        categoriaRepository.save(categoria);

        LivroRequest request = new LivroRequest("Titulo do Livro", "Resumo do livro... (até 500 caracteres)", "Sumário do livro...",
                BigDecimal.valueOf(29.99) , 200, "978-3-16-148410-7", LocalDate.of(2024,12,31),
                categoria.getId(), autor.getId());
        var livro = livroRepository.save(request.toModel(categoriaRepository, autorRepository));

        String responseBody = mockMvc.perform(MockMvcRequestBuilders.get("/livro/{id}",livro.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @Transactional
    public void shouldReturn404() throws Exception {
        Autor autor = new Autor("Eduardo", "caet@email.com", "Descrição do autor");
        autorRepository.save(autor);

        Categoria categoria = new Categoria("Ficção Fantastica");
        categoriaRepository.save(categoria);

        LivroRequest request = new LivroRequest("Titulo do Livro", "Resumo do livro... (até 500 caracteres)", "Sumário do livro...",
                BigDecimal.valueOf(29.99) , 200, "978-3-16-148410-7", LocalDate.of(2024,12,31),
                categoria.getId(), autor.getId());
        var livro = livroRepository.save(request.toModel(categoriaRepository, autorRepository));

        String responseBody = mockMvc.perform(MockMvcRequestBuilders.get("/livro/{id}",0)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}