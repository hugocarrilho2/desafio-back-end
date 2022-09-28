package com.example.testeBackEnd.services;

import com.example.testeBackEnd.dto.DtoSetor;
import com.example.testeBackEnd.model.Setor;

import java.util.Collection;
import java.util.List;

public interface SetorService
{
    void verificaSetorExistente(DtoSetor dtoSetor) throws Exception;

    Setor salvar(DtoSetor dtoSetor);

    List<Setor> listarSetores();

    Setor buscarSetor(Long id);
}
