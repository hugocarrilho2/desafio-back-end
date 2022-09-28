package com.example.testeBackEnd.services.impl;

import com.example.testeBackEnd.dto.DtoCargo;
import com.example.testeBackEnd.dto.DtoSetor;
import com.example.testeBackEnd.exceptions.BusinessException;
import com.example.testeBackEnd.model.Cargo;
import com.example.testeBackEnd.model.Setor;
import com.example.testeBackEnd.repository.CargoRepository;
import com.example.testeBackEnd.services.CargoService;
import com.example.testeBackEnd.services.SetorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class CargoServiceImpl implements CargoService
{
    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private SetorService setorService;

    @Override
    @Transactional(readOnly = true)
    public void verificaCargoExistente(List<DtoCargo> dtoCargo) throws Exception{
        for(DtoCargo cargo: dtoCargo){
            if(cargoRepository.existsByNome(cargo.getNome())){
                throw new BusinessException("Já existe um cargo com este nome");
            }
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void salvar(List<DtoCargo> dtoCargo, Setor setor){
        Setor setorEncontrado = setorService.buscarSetor(setor.getId());
        for(DtoCargo cargo: dtoCargo){
            Cargo novoCargo = new Cargo();
            BeanUtils.copyProperties(cargo, novoCargo);
            setorEncontrado.adicionarCargo(novoCargo);
        }
    }
}
