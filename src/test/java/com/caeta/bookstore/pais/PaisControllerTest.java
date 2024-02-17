package com.caeta.bookstore.pais;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PaisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaisRepository repository;

    @Test
    @Transactional
    void createPais() throws Exception {

        PaisRequest request = new PaisRequest("Brasil");

        String jsonRequest = objectMapper.writeValueAsString(request);

        ResultActions resultActions = mockMvc.perform(post("/pais")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        var responseBody = resultActions.andReturn().getResponse().getContentAsString();
        var responseStatusCode = resultActions.andReturn().getResponse().getStatus();

        assertTrue(responseBody.contains("Brasil"));
        assertEquals(201, responseStatusCode);
    }

    @Test
    @Transactional
    void shouldNotCreateAPaisWithARepeatedNome() throws Exception {
        repository.save(new Pais("Brasil"));

        PaisRequest request = new PaisRequest("Brasil");

        String jsonRequest = objectMapper.writeValueAsString(request);

        ResultActions resultActions = mockMvc.perform(post("/pais")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        var responseBody = resultActions.andReturn().getResponse().getContentAsString();
        var responseStatusCode = resultActions.andReturn().getResponse().getStatus();

        assertTrue(responseBody.contains("Esse pais ja existe"));
        assertEquals(400, responseStatusCode);
    }
}