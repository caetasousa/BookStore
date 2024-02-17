package com.caeta.bookstore.estado;

import com.caeta.bookstore.pais.Pais;
import com.caeta.bookstore.pais.PaisRepository;
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
class EstadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Test
    @Transactional
    void createEstado() throws Exception {
        Pais pais = new Pais("Brasil");
        paisRepository.save(pais);

        EstadoRequest estado = new EstadoRequest("Mato Grosso", pais.getId());

        String jsonRequest = objectMapper.writeValueAsString(estado);

        ResultActions resultActions = mockMvc.perform(post("/estado")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        var responseStatusCode = resultActions.andReturn().getResponse().getStatus();
        assertEquals(201, responseStatusCode);
    }

    @Test
    @Transactional
    public void shouldNotCreateAnEstadoWithInexistentIdPaisAndRepetibleNamePais() throws Exception {
        Pais pais = new Pais("Brasil");
        paisRepository.save(pais);

        EstadoRequest estado = new EstadoRequest("Mato Grosso", pais.getId());
        estadoRepository.save(estado.toModel(paisRepository));

        EstadoRequest request = new EstadoRequest("Mato Grosso", 9999L);

        String jsonRequest = objectMapper.writeValueAsString(request);

        ResultActions resultActions = mockMvc.perform(post("/estado")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        var responseBody = resultActions.andReturn().getResponse().getContentAsString();
        var responseStatusCode = resultActions.andReturn().getResponse().getStatus();

        assertTrue(responseBody.contains("Esse estado ja existe"));
        assertTrue(responseBody.contains("Esse pais nao existe"));
        assertEquals(400, responseStatusCode);
    }
}