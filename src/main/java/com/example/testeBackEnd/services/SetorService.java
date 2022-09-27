package com.example.testeBackEnd.services;

import com.example.testeBackEnd.dto.DtoSetor;
import com.example.testeBackEnd.model.Setor;

public interface SetorService
{
    void verificaSetorExistente(DtoSetor dtoSetor) throws Exception;

    void salvar(DtoSetor dtoSetor);



}
