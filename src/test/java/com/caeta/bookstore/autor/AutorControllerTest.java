package com.caeta.bookstore.autor;

import com.caeta.bookstore.autor.exeption.ErrorDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
        // Crie um objeto AutorRequest para simular a criação de um novo Autor
        AutorRequest request = new AutorRequest("Eduardo", "caet@email.com", "Descrição do autor");

        // Converta o objeto AutorRequest para JSON
        String jsonRequest = objectMapper.writeValueAsString(request);

        // Realize a requisição POST para criar um novo Autor
        mockMvc.perform(post("/autor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void shouldNotCreateAnAuthorWithARepeatedEmail() throws Exception {
        // Salva um autor no banco de dados para verificação de duplicidade de email
        autorRepository.save(new Autor("Eduardo", "caet@email.com", "Descrição do autor"));
        // Crie um objeto AutorRequest para simular a criação de um novo Autor
        AutorRequest request = new AutorRequest("Eduardo", "caet@email.com", "Descrição do autor");
        // Converta o objeto AutorRequest para JSON
        String jsonRequest = objectMapper.writeValueAsString(request);
        // Realiza a requisição POST para criar um novo Autor
        ResultActions resultActions = mockMvc.perform(post("/autor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());

        Collection<String>  valueErro = convertResponseBody(resultActions);

        assertTrue(valueErro.contains("This email is already registered"));
    }

    private Collection<String> convertResponseBody(ResultActions resultActions) throws UnsupportedEncodingException, JsonProcessingException {
        // Obtém o MvcResult para acessar o conteúdo do ResponseBody
        MvcResult result = resultActions.andReturn();
        String content = result.getResponse().getContentAsString();
        // Converte o conteúdo JSON do ResponseBody para um objeto ErrorDTO usando o ObjectMapper
        ErrorDTO errorResponse = objectMapper.readValue(content, ErrorDTO.class);
        Collection<String> valueErro = errorResponse.getErros().values();

        return valueErro;
    }
}
































