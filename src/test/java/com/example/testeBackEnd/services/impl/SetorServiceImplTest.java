package com.example.testeBackEnd.services.impl;

import com.example.testeBackEnd.dto.DtoCargo;
import com.example.testeBackEnd.dto.DtoSetor;
import com.example.testeBackEnd.exceptions.BusinessException;
import com.example.testeBackEnd.exceptions.NotFoundException;
import com.example.testeBackEnd.model.Cargo;
import com.example.testeBackEnd.model.Setor;
import com.example.testeBackEnd.repository.SetorRepository;
import com.example.testeBackEnd.services.CargoService;
import com.example.testeBackEnd.services.SetorService;
import com.example.testeBackEnd.utils.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class SetorServiceImplTest {

    @Autowired
    SetorService setorService;

    @MockBean
    SetorRepository setorRepository;

    @MockBean
    CargoService cargoService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testeDeveVerificaSeSetorJaExistente() {
        when(setorRepository.existsByNome(anyString())).thenReturn(false);

        setorService.verificaSetorExistente(Data.dtoSetor);

        verify(setorRepository, times(1)).existsByNome(anyString());
    }

    @Test
    void testeLancarUmaExecptionQuandoUmSetorJaExiste() {
        when(setorRepository.existsByNome(anyString())).thenReturn(true);

        assertThrows(BusinessException.class, () -> setorService.verificaSetorExistente(Data.dtoSetor));
    }

    @Test
    void testeDeveSalvarUmNovoSetor() {
        when(setorRepository.save(any(Setor.class))).thenReturn(Data.setor);

        Setor setor = setorService.salvar(Data.dtoSetor);

        assertThat(setor.getNome()).isEqualTo(Data.setor.getNome());
    }

    @Test
    void testeDeveListarTodosSetores() {
        when(setorRepository.findAll()).thenReturn(List.of(Data.setor));

        List<DtoSetor> setores = setorService.listarSetores();

        assertThat(setores.size()).isEqualTo(1);
        verify(setorRepository, times(1)).findAll();
    }

    @Test
    void testeDeveBuscarSetorUmSetor() {
        when(setorRepository.findById(anyLong())).thenReturn(Optional.of(Data.setor));

        setorService.buscarSetor(1L);

        verify(setorRepository, times(1)).findById(anyLong());
    }

    @Test
    void testeDeveLancarUmaExecptionQuandoNaoEncontrarUmSetor() {
        when(setorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> setorService.buscarSetor(anyLong()));
    }

    @Test
    void testeBuscarDetalhesDeUmSetorExistente() {
        when(setorRepository.findById(anyLong())).thenReturn(Optional.of(Data.setor));

        DtoSetor dtoSetor = setorService.buscarDetalhesSetor(1L);

        assertThat(dtoSetor.getNome()).isEqualTo(Data.dtoSetor.getNome());
    }

    @Test
    void testeDeveExcluirUmSetor() {
        when(setorRepository.findById(anyLong())).thenReturn(Optional.of(Data.setor));

        setorService.excluirSetor(1L);

        verify(setorRepository, times(1)).delete(any(Setor.class));
    }

    @Test
    void testeDeveAtualizarUmSetor() {
        when(setorRepository.findById(anyLong())).thenReturn(Optional.of(Data.setor2));

        setorService.atualizarSetor(Data.dtoSetor, Data.setor2.getId());

        assertThat(Data.setor2.getCargos().size()).isEqualTo(0);
        assertThat(Data.setor2.getNome()).isEqualTo(Data.dtoSetor.getNome());
    }

    @Test
    void testeDeveVerificaInexistenciaCargo() {
        boolean result = setorService.verificaInexistenciaCargo(List.of(Data.cargo), "TI");

        assertThat(result).isTrue();
    }
}