package com.example.testeBackEnd.services.impl;


import com.example.testeBackEnd.dto.DtoSetor;
import com.example.testeBackEnd.exceptions.BusinessException;
import com.example.testeBackEnd.model.Setor;
import com.example.testeBackEnd.repository.SetorRepository;
import com.example.testeBackEnd.services.SetorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetorServiceImpl implements SetorService
{
    @Autowired
    private SetorRepository setorRepository;

    @Override
    public void verificaSetorExistente(DtoSetor dtoSetor){
        if(setorRepository.existsByNome(dtoSetor.getNome())){
            throw new BusinessException("Já existe um setor com este nome");
        }
    }

    @Override
    public void salvar(DtoSetor dtoSetor)
    {
        Setor setor = new Setor();
        BeanUtils.copyProperties(dtoSetor, setor);
        setorRepository.save(setor);
    }
}
