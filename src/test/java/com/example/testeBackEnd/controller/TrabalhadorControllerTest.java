package com.example.testeBackEnd.controller;

import com.example.testeBackEnd.dto.DtoSetor;
import com.example.testeBackEnd.exceptions.BusinessException;
import com.example.testeBackEnd.exceptions.NotFoundException;
import com.example.testeBackEnd.services.TrabalhadorService;
import com.example.testeBackEnd.utils.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrabalhadorController.class)
class TrabalhadorControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TrabalhadorService trabalhadorService;

    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void testeDeveSalvarTrabalhador() throws Exception {
        String conteudoRequest = mapper.writeValueAsString(Data.trabalhadorParaCriar);

        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .post("/trabalhadores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(conteudoRequest));

        request.andExpect(status().isCreated());
    }

    @Test
    void testeDeveRetornarErroQuandoJaExistirUmTrabalhadorComOMesmoCPF() throws Exception {
        String messagem = "Já existe um trabalhador cadastrado com o CPF informado.";
        doThrow(new BusinessException(messagem)).when(trabalhadorService).verificaCpfExistente(anyString());

        String conteudoRequest = mapper.writeValueAsString(Data.trabalhadorParaCriar);

        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .post("/trabalhadores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(conteudoRequest));

        request.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.message").value(messagem));
    }

    @Test
    void testeDeveBuscarTrabalhador() throws Exception {
        when(trabalhadorService.buscarDetalhesTrabalhador(anyLong())).thenReturn(Data.dtoTrabalhador);

        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .get("/trabalhadores/1")
                .contentType(MediaType.APPLICATION_JSON));

        request.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("Joao"));
    }

    @Test
    void testeDeveRetornarErroQuandoNaoExistirOTrabalhador() throws Exception {
        String messagem = "Trabalhador não encontrado.";
        doThrow(new NotFoundException(messagem)).when(trabalhadorService).buscarDetalhesTrabalhador(anyLong());

        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .get("/trabalhadores/1")
                .contentType(MediaType.APPLICATION_JSON));

        request.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.message").value(messagem));
    }

    @Test
    void testeDeveListarTrabalhadores() throws Exception {
        when(trabalhadorService.listarTrabalhadores()).thenReturn(List.of(Data.dtoTrabalhador));

        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .get("/trabalhadores")
                .contentType(MediaType.APPLICATION_JSON));

        request.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Joao"))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testeDeveAtualizarTrabalhador() throws Exception {
        String conteudoRequest = mapper.writeValueAsString(Data.trabalhadorParaAtualizar);

        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .put("/trabalhadores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(conteudoRequest));

        request.andExpect(status().isNoContent());
    }

    @Test
    void testeDeveExcluirTrabalhador() throws Exception {
        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .delete("/trabalhadores/1")
                .contentType(MediaType.APPLICATION_JSON));

        request.andExpect(status().isNoContent());

    }
}