package com.example.testeBackEnd.services;

import com.example.testeBackEnd.dto.DtoTrabalhador;
import com.example.testeBackEnd.model.Trabalhador;

import java.util.List;

public interface TrabalhadorService {

    void verificaCpfExistente(String cpf);

    void salvarTrabalhador(DtoTrabalhador dtoTrabalhador);

    Trabalhador buscarTrabalhador(Long id);

    void atualizarTrabalhador(DtoTrabalhador dtoTrabalhador, Long id);

    DtoTrabalhador buscarDetalhesTrabalhador(Long id);

    void excluirTrabalhador(Long id);

    List<DtoTrabalhador> listarTrabalhadores();
}
