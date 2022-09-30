package com.example.testeBackEnd.controller;

import com.example.testeBackEnd.dto.DtoSetor;
import com.example.testeBackEnd.exceptions.BusinessException;
import com.example.testeBackEnd.exceptions.NotFoundException;
import com.example.testeBackEnd.services.CargoService;
import com.example.testeBackEnd.services.SetorService;
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

@WebMvcTest(SetorController.class)
class SetorControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    SetorService setorService;

    @MockBean
    CargoService cargoService;

    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void testeDeveSalvarSetor() throws Exception {
        String conteudoRequest = mapper.writeValueAsString(Data.setorParaCriar);

        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .post("/setores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(conteudoRequest));

        request.andExpect(status().isCreated())
                .andExpect(content().string("Setor criado com sucesso!"));
    }

    @Test
    void testeDeveRetornarErrorAoTentarSalvarUmSetor() throws Exception {
        String messagem = "Já existe um setor com o nome: " + Data.setor.getNome();
        doThrow(new BusinessException(messagem)).when(setorService).verificaSetorExistente(any(DtoSetor.class));

        String conteudoRequest = mapper.writeValueAsString(Data.setorParaCriar);

        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .post("/setores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(conteudoRequest));

        request.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.message").value(messagem));
    }

    @Test
    void testeDeveListarSetores() throws Exception {
        when(setorService.listarSetores()).thenReturn(List.of(Data.dtoSetor));

        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .get("/setores")
                .contentType(MediaType.APPLICATION_JSON));

        request.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testeDeveBuscarSetor() throws Exception {
        when(setorService.buscarDetalhesSetor(anyLong())).thenReturn(Data.dtoSetor);

        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .get("/setores/1")
                .contentType(MediaType.APPLICATION_JSON));

        request.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("TI"));
    }

    @Test
    void testeDeveRetornarErroAoTentarBuscarSetor() throws Exception {
        String messagem = "Setor não encontrado";
        doThrow(new NotFoundException(messagem)).when(setorService).buscarDetalhesSetor(anyLong());

        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .get("/setores/999")
                .contentType(MediaType.APPLICATION_JSON));

        request.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.message").value(messagem));
    }

    @Test
    void testeDeveAtualizarSetor() throws Exception {
        String conteudoRequest = mapper.writeValueAsString(Data.setorParaAtualizar);

        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .put("/setores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(conteudoRequest));

        request.andExpect(status().isNoContent());
    }

    @Test
    void testeDeveExcluirSetor() throws Exception {
        ResultActions request = mvc.perform(MockMvcRequestBuilders
                .delete("/setores/1")
                .contentType(MediaType.APPLICATION_JSON));

        request.andExpect(status().isNoContent());
    }
}