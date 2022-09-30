package com.example.testeBackEnd.services.impl;

import com.example.testeBackEnd.dto.DtoTrabalhador;
import com.example.testeBackEnd.exceptions.BusinessException;
import com.example.testeBackEnd.exceptions.NotFoundException;
import com.example.testeBackEnd.model.Trabalhador;
import com.example.testeBackEnd.repository.TrabalhadorRepository;
import com.example.testeBackEnd.services.CargoService;
import com.example.testeBackEnd.services.SetorService;
import com.example.testeBackEnd.services.TrabalhadorService;
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
class TrabalhadorServiceImplTest {

    @Autowired
    TrabalhadorService trabalhadorService;

    @MockBean
    SetorService setorService;

    @MockBean
    CargoService cargoService;

    @MockBean
    TrabalhadorRepository trabalhadorRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testeDeveVerificaCpfExistente() {
        when(trabalhadorRepository.existsByCpf(anyString())).thenReturn(false);

        assertDoesNotThrow(() -> trabalhadorService.verificaCpfExistente(Data.trabalhador.getCpf()));
    }

    @Test
    void testeDeveLancarExceptionQuandoJaExistirUmCpfCadastrado() {
        when(trabalhadorRepository.existsByCpf(anyString())).thenReturn(true);

        assertThrows(BusinessException.class, () -> trabalhadorService.verificaCpfExistente(Data.trabalhador.getCpf()));
    }

    @Test
    void testeDeveSalvarTrabalhador() {
        when(setorService.buscarSetor(anyLong())).thenReturn(Data.setor);
        when(cargoService.buscarCargo(anyLong())).thenReturn(Data.cargo);

        trabalhadorService.salvarTrabalhador(Data.dtoTrabalhador);

        verify(trabalhadorRepository, times(1)).save(any(Trabalhador.class));
    }

    @Test
    void testeDeveBuscarTrabalhador() {
        when(trabalhadorRepository.findById(anyLong())).thenReturn(Optional.of(Data.trabalhador));

        Trabalhador trabalhador = trabalhadorService.buscarTrabalhador(Data.trabalhador.getId());

        assertNotNull(trabalhador);
        assertThat(trabalhador.getNome()).isEqualTo(Data.trabalhador.getNome());
    }

    @Test
    void testeDeveLancarUmExceptionQUandoNaoEncontrarUmTrabalhador() {
        when(trabalhadorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> trabalhadorService.buscarTrabalhador(Data.trabalhador.getId()));
    }

    @Test
    void testeDeveAtualizarTrabalhador() {
        when(trabalhadorRepository.findById(anyLong())).thenReturn(Optional.of(Data.trabalhador));
        when(cargoService.buscarCargo(anyLong())).thenReturn(Data.cargo);
        when(setorService.buscarSetor(anyLong())).thenReturn(Data.setor);

        trabalhadorService.atualizarTrabalhador(Data.dtoTrabalhador2, Data.dtoTrabalhador.getId());

        assertThat(Data.trabalhador.getNome()).isEqualTo(Data.dtoTrabalhador2.getNome());
    }

    @Test
    void testeDeveListarTrabalhadores() {
        when(trabalhadorRepository.findAll()).thenReturn(List.of(Data.trabalhador));

        List<DtoTrabalhador> trabalhadores = trabalhadorService.listarTrabalhadores();

        assertThat(trabalhadores.size()).isEqualTo(1);
    }

    @Test
    void testeDeveBuscarDetalhesDoTrabalhador() {
        when(trabalhadorRepository.findById(anyLong())).thenReturn(Optional.of(Data.trabalhador));

        DtoTrabalhador dtoTrabalhador = trabalhadorService.buscarDetalhesTrabalhador(1L);

        assertThat(dtoTrabalhador.getNome()).isEqualTo(Data.trabalhador.getNome());
        verify(trabalhadorRepository, times(1)).findById(anyLong());
    }

    @Test
    void testeDeveExcluirTrabalhador() {
        when(trabalhadorRepository.findById(anyLong())).thenReturn(Optional.of(Data.trabalhador));

        trabalhadorService.excluirTrabalhador(1L);

        verify(trabalhadorRepository, times(1)).delete(any(Trabalhador.class));
    }

}