package com.caeta.bookstore.autor;

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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    @Transactional
    public void shouldCreateAnAutor() throws Exception {
        AutorRequest request = new AutorRequest("Eduardo", "caet@email.com", "Descrição do autor");

        String jsonRequest = objectMapper.writeValueAsString(request);

        ResultActions resultActions = mockMvc.perform(post("/autor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest));

        var responseBody = resultActions.andReturn().getResponse().getContentAsString();
        var responseStatusCode = resultActions.andReturn().getResponse().getStatus();

        assertTrue(responseBody.contains(LocalDate.now().toString()));
        assertTrue(responseBody.contains("Eduardo"));
        assertTrue(responseBody.contains("caet@email.com"));
        assertTrue(responseBody.contains("Descrição do autor"));
        assertEquals(201, responseStatusCode);
    }

    @Test
    @Transactional
    public void shouldNotCreateAnAuthorWithARepeatedEmail() throws Exception {
        autorRepository.save(new Autor("Eduardo", "caet@email.com", "Descrição do autor"));

        AutorRequest request = new AutorRequest("Eduardo", "caet@email.com", "Descrição do autor");

        String jsonRequest = objectMapper.writeValueAsString(request);

        ResultActions resultActions = mockMvc.perform(post("/autor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest));

        var responseBody = resultActions.andReturn().getResponse().getContentAsString();
        var responseStatusCode = resultActions.andReturn().getResponse().getStatus();

        assertTrue(responseBody.contains("This email is already registered"));
        assertEquals(400, responseStatusCode);
    }
}