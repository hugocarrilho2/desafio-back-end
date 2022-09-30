package com.example.testeBackEnd.services;

import com.example.testeBackEnd.dto.DtoCargo;
import com.example.testeBackEnd.dto.DtoSetor;
import com.example.testeBackEnd.model.Cargo;
import com.example.testeBackEnd.model.Setor;

import java.util.Collection;
import java.util.List;

public interface CargoService
{
    void verificaCargoExistente(List<DtoCargo> dtoCargo);

    void salvar(List<DtoCargo> dtoCargo, Setor setor);

    Cargo buscarCargo(Long id);

    DtoCargo buscarDetalhesCargo(Long id);
}
