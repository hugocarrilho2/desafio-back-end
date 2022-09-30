package com.example.testeBackEnd.services.impl;

import com.example.testeBackEnd.dto.DtoCargo;
import com.example.testeBackEnd.exceptions.BusinessException;
import com.example.testeBackEnd.exceptions.NotFoundException;
import com.example.testeBackEnd.model.Cargo;
import com.example.testeBackEnd.model.Setor;
import com.example.testeBackEnd.repository.CargoRepository;
import com.example.testeBackEnd.services.CargoService;
import com.example.testeBackEnd.services.SetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {
    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private SetorService setorService;

    @Override
    @Transactional(readOnly = true)
    public void verificaCargoExistente(List<DtoCargo> dtoCargo) {
        for (DtoCargo cargo : dtoCargo) {
            if (cargoRepository.existsByNome(cargo.getNome())) {
                throw new BusinessException("Já existe um cargo com o nome: " + cargo.getNome());
            }
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void salvar(List<DtoCargo> dtoCargo, Setor setor) {
        Setor setorEncontrado = setorService.buscarSetor(setor.getId());

        for (DtoCargo cargo : dtoCargo) {
            Cargo novoCargo = Cargo.converterParaCargo(cargo);
            novoCargo.setId(null);
            setorEncontrado.adicionarCargo(novoCargo);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Cargo buscarCargo(Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cargo não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public DtoCargo buscarDetalhesCargo(Long id) {
        Cargo cargoEncontrado = buscarCargo(id);
        return Cargo.converterParaDto(cargoEncontrado);
    }
}
