package com.caeta.bookstore.categoria;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoriaRepository repository;

    @Test
    @Transactional
    void shouldCreateAnCategoria() throws Exception {
        CategoriaRequest request = new CategoriaRequest("Ficção Fantastica");

        String jsonRequest = objectMapper.writeValueAsString(request);

        ResultActions resultActions = mockMvc.perform(post("/categoria")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        var responseBody = resultActions.andReturn().getResponse().getContentAsString();
        var responseStatusCode = resultActions.andReturn().getResponse().getStatus();

        assertTrue(responseBody.contains("Ficção Fantastica"));
        assertEquals(201, responseStatusCode);
    }

    @Test
    @Transactional
    void shouldNotCreateAnCategoriaWithARepeatedNome() throws Exception {
        repository.save(new Categoria("Ficção Fantastica"));

        CategoriaRequest request = new CategoriaRequest("Ficção Fantastica");

        String jsonRequest = objectMapper.writeValueAsString(request);

        ResultActions resultActions = mockMvc.perform(post("/categoria")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        var responseBody = resultActions.andReturn().getResponse().getContentAsString();
        var responseStatusCode = resultActions.andReturn().getResponse().getStatus();

        assertTrue(responseBody.contains("Esse nome ja existe"));
        assertEquals(400, responseStatusCode);
    }
}