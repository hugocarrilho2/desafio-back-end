package com.example.testeBackEnd.services.impl;

import com.example.testeBackEnd.dto.DtoCargo;
import com.example.testeBackEnd.exceptions.BusinessException;
import com.example.testeBackEnd.exceptions.NotFoundException;
import com.example.testeBackEnd.model.Cargo;
import com.example.testeBackEnd.repository.CargoRepository;
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
class CargoServiceImplTest {

    @Autowired
    CargoService cargoService;

    @MockBean
    CargoRepository cargoRepository;

    @MockBean
    SetorService setorService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testeDeveVerificarSeCargoExistente() {
        when(cargoRepository.existsByNome(anyString())).thenReturn(false);

        cargoService.verificaCargoExistente(List.of(Data.dtoCargo));

        verify(cargoRepository, times(1)).existsByNome(anyString());
    }

    @Test
    void testeDeveLancarExceptionQuandoJaExistirOCargo() {
        when(cargoRepository.existsByNome(anyString())).thenReturn(true);

        assertThrows(BusinessException.class, () -> cargoService.verificaCargoExistente(List.of(Data.dtoCargo)));
    }

    @Test
    void testeDeveSalvarCargo() {
        when(setorService.buscarSetor(anyLong())).thenReturn(Data.setor);

        cargoService.salvar(List.of(Data.dtoCargo), Data.setor);

        assertThat(Data.setor.getCargos().size()).isEqualTo(1);
        assertThat(Data.setor.getCargos().get(0).getNome()).isEqualTo(Data.dtoCargo.getNome());
    }

    @Test
    void testeDeveBuscarCargo() {
        when(cargoRepository.findById(anyLong())).thenReturn(Optional.of(Data.cargo));

        Cargo cargo = cargoService.buscarCargo(Data.cargo.getId());

        verify(cargoRepository, times(1)).findById(anyLong());
        assertThat(cargo.getNome()).isEqualTo(Data.cargo.getNome());
    }

    @Test
    void testeDeveLancarExceptionQuandoNaoEncontrarOCargo() {
        when(cargoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cargoService.buscarCargo(anyLong()));
    }

    @Test
    void testeDeveBuscarDetalhesCargo() {
        when(cargoRepository.findById(anyLong())).thenReturn(Optional.of(Data.cargo));

        DtoCargo dtoCargo = cargoService.buscarDetalhesCargo(1L);

        assertThat(dtoCargo.getNome()).isEqualTo(Data.dtoCargo.getNome());
    }
}