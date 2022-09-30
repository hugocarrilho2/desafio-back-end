package com.example.testeBackEnd.services;

import com.example.testeBackEnd.dto.DtoSetor;
import com.example.testeBackEnd.model.Cargo;
import com.example.testeBackEnd.model.Setor;

import java.util.List;

public interface SetorService {
    void verificaSetorExistente(DtoSetor dtoSetor);

    Setor salvar(DtoSetor dtoSetor);

    List<DtoSetor> listarSetores();

    Setor buscarSetor(Long id);

    DtoSetor buscarDetalhesSetor(Long id);

    void excluirSetor(Long id);

    void atualizarSetor(DtoSetor dtoSetor, Long idSetor);

    boolean verificaInexistenciaCargo(List<Cargo> cargos, String nome);
}
