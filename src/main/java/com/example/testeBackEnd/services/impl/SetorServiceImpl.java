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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetorServiceImpl implements SetorService {
    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private CargoService cargoService;

    @Override
    @Transactional(readOnly = true)
    public void verificaSetorExistente(DtoSetor dtoSetor) {
        if (setorRepository.existsByNome(dtoSetor.getNome())) {
            throw new BusinessException("Já existe um setor com o nome: " + dtoSetor.getNome());
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Setor salvar(DtoSetor dtoSetor) {
        Setor setor = Setor.converterParaSetor(dtoSetor);
        setor.setId(null);

        return setorRepository.save(setor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DtoSetor> listarSetores() {
        List<Setor> setoresEncontrados = setorRepository.findAll();
        List<DtoSetor> setores = new ArrayList<>();

        setoresEncontrados.forEach(setor -> {
            DtoSetor dtoSetor = Setor.converterParaDto(setor);

            List<DtoCargo> cargos = new ArrayList<>();
            setor.getCargos().forEach(cargo -> {
                DtoCargo dtoCargo = Cargo.converterParaDto(cargo);
                cargos.add(dtoCargo);
            });
            dtoSetor.setCargos(cargos);

            setores.add(dtoSetor);
        });

        return setores;
    }

    @Override
    @Transactional(readOnly = true)
    public Setor buscarSetor(Long id) {
        return setorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Setor não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public DtoSetor buscarDetalhesSetor(Long id) {
        Setor setorEncontrado = buscarSetor(id);

        DtoSetor dtoSetor = Setor.converterParaDto(setorEncontrado);

        List<DtoCargo> cargos = new ArrayList<>();
        setorEncontrado.getCargos().forEach(cargo -> {
            DtoCargo dtoCargo = Cargo.converterParaDto(cargo);
            cargos.add(dtoCargo);
        });
        dtoSetor.setCargos(cargos);

        return dtoSetor;
    }

    @Override
    @Transactional(readOnly = false)
    public void excluirSetor(Long id) {
        Setor setorEncontrado = buscarSetor(id);

        setorRepository.delete(setorEncontrado);
    }

    @Override
    @Transactional(readOnly = false)
    public void atualizarSetor(DtoSetor dtoSetor, Long idSetor) {
        Setor setorEncontrado = buscarSetor(idSetor);
        setorEncontrado.setNome(dtoSetor.getNome());

        List<Cargo> cargos = dtoSetor.getCargos().stream().map(Cargo::converterParaCargo).collect(Collectors.toList());

        List<Cargo> cargosParaRemover = new ArrayList<>();
        for (Cargo cargo : setorEncontrado.getCargos()) {
            if (verificaInexistenciaCargo(cargos, cargo.getNome())) cargosParaRemover.add(cargo);
        }

        for (Cargo cargo : cargos) {
            if (verificaInexistenciaCargo(setorEncontrado.getCargos(), cargo.getNome())) setorEncontrado.adicionarCargo(cargo);
        }

        for (Cargo cargo : cargosParaRemover) {
            setorEncontrado.removerCargo(cargo);
        }
    }

    public boolean verificaInexistenciaCargo(List<Cargo> cargos, String nome){
        return cargos.stream().noneMatch(cargo -> cargo.getNome().equals(nome));
    }
}
