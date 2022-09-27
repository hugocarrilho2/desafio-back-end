package com.example.testeBackEnd.controller;

import com.example.testeBackEnd.dto.DtoSetor;
import com.example.testeBackEnd.repository.SetorRepository;
import com.example.testeBackEnd.services.SetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/setores")
public class SetorController
{
    @Autowired
    private SetorService setorService;

    @PostMapping
    public ResponseEntity<?> buscarSetores(@RequestBody DtoSetor dtoSetor) throws Exception {
        setorService.verificaSetorExistente(dtoSetor);
        setorService.salvar(dtoSetor);

        return ResponseEntity.status(HttpStatus.CREATED).body("Setor criado com sucesso!");
    }

}
