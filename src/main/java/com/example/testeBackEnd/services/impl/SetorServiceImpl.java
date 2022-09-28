package com.example.testeBackEnd.services.impl;


import com.example.testeBackEnd.dto.DtoSetor;
import com.example.testeBackEnd.exceptions.BusinessException;
import com.example.testeBackEnd.model.Setor;
import com.example.testeBackEnd.repository.SetorRepository;
import com.example.testeBackEnd.services.SetorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SetorServiceImpl implements SetorService
{
    @Autowired
    private SetorRepository setorRepository;

    @Override
    @Transactional(readOnly = true)
    public void verificaSetorExistente(DtoSetor dtoSetor){
        if(setorRepository.existsByNome(dtoSetor.getNome())){
            throw new BusinessException("Já existe o setor com nome: " + dtoSetor.getNome());
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Setor salvar(DtoSetor dtoSetor){
        Setor setor = new Setor();
        BeanUtils.copyProperties(dtoSetor, setor);
        return setorRepository.save(setor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Setor> listarSetores(){
        return setorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Setor buscarSetor(Long id){
        Optional<Setor> result = setorRepository.findById(id);
        if(result.isEmpty()){
            throw new BusinessException("Setor não encontrado");
        }
        return result.get();
    }
}
