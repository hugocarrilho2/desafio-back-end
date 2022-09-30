package com.example.testeBackEnd.services.impl;

import com.example.testeBackEnd.dto.DtoTrabalhador;
import com.example.testeBackEnd.exceptions.BusinessException;
import com.example.testeBackEnd.exceptions.NotFoundException;
import com.example.testeBackEnd.model.Cargo;
import com.example.testeBackEnd.model.Setor;
import com.example.testeBackEnd.model.Trabalhador;
import com.example.testeBackEnd.repository.TrabalhadorRepository;
import com.example.testeBackEnd.services.CargoService;
import com.example.testeBackEnd.services.SetorService;
import com.example.testeBackEnd.services.TrabalhadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrabalhadorServiceImpl implements TrabalhadorService {

    @Autowired
    private SetorService setorService;

    @Autowired
    private CargoService cargoService;

    @Autowired
    private TrabalhadorRepository trabalhadorRepository;

    @Override
    @Transactional(readOnly = true)
    public void verificaCpfExistente(String cpf) {
        if (trabalhadorRepository.existsByCpf(cpf)) {
            throw new BusinessException("Já existe um trabalhador cadastrado com o CPF informado.");
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void salvarTrabalhador(DtoTrabalhador dtoTrabalhador) {
        Trabalhador trabalhador = Trabalhador.converterParaTrabalhador(dtoTrabalhador);
        trabalhador.setId(null);

        trabalhador.setSetor(setorService.buscarSetor(dtoTrabalhador.getSetor().getId()));
        trabalhador.setCargo(cargoService.buscarCargo(dtoTrabalhador.getCargo().getId()));

        trabalhadorRepository.save(trabalhador);
    }

    @Override
    @Transactional(readOnly = true)
    public Trabalhador buscarTrabalhador(Long id) {
        return trabalhadorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trabalhador não encontrado."));
    }

    @Override
    @Transactional(readOnly = false)
    public void atualizarTrabalhador(DtoTrabalhador dtoTrabalhador, Long id) {
        Trabalhador trabalhadorEncontrado = buscarTrabalhador(id);

        if (!trabalhadorEncontrado.getNome().equals(dtoTrabalhador.getNome())) {
            trabalhadorEncontrado.setNome(dtoTrabalhador.getNome());
        }

        if (!dtoTrabalhador.getCargo().getId().equals(trabalhadorEncontrado.getCargo().getId())) {
            trabalhadorEncontrado.setCargo(cargoService.buscarCargo(dtoTrabalhador.getCargo().getId()));
        }

        if (!dtoTrabalhador.getSetor().getId().equals(trabalhadorEncontrado.getSetor().getId())) {
            trabalhadorEncontrado.setSetor(setorService.buscarSetor(dtoTrabalhador.getSetor().getId()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DtoTrabalhador buscarDetalhesTrabalhador(Long id) {
        Trabalhador trabalhadorEncontrado = buscarTrabalhador(id);
        DtoTrabalhador dtoTrabalhador = Trabalhador.converterParaDto(trabalhadorEncontrado);

        dtoTrabalhador.setSetor(Setor.converterParaDto(trabalhadorEncontrado.getSetor()));
        dtoTrabalhador.getSetor().setCargos(null);

        dtoTrabalhador.setCargo(Cargo.converterParaDto(trabalhadorEncontrado.getCargo()));

        return dtoTrabalhador;
    }

    @Override
    @Transactional(readOnly = false)
    public void excluirTrabalhador(Long id) {
        Trabalhador trabalhadorEncontrado = this.buscarTrabalhador(id);

        trabalhadorRepository.delete(trabalhadorEncontrado);
    }

    @Override
    public List<DtoTrabalhador> listarTrabalhadores() {
        List<Trabalhador> trabalhadoresEncontrados = trabalhadorRepository.findAll();
        List<DtoTrabalhador> trabalhadores = new ArrayList<>();

        trabalhadoresEncontrados.forEach(trabalhador -> {
            DtoTrabalhador dtoTrabalhador = Trabalhador.converterParaDto(trabalhador);

            dtoTrabalhador.setSetor(Setor.converterParaDto(trabalhador.getSetor()));
            dtoTrabalhador.getSetor().setCargos(null);

            dtoTrabalhador.setCargo(Cargo.converterParaDto(trabalhador.getCargo()));

            trabalhadores.add(dtoTrabalhador);
        });

        return trabalhadores;
    }

}
