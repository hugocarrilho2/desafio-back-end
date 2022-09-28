package com.example.testeBackEnd.controller;

import com.example.testeBackEnd.dto.DtoSetor;
import com.example.testeBackEnd.model.Setor;
import com.example.testeBackEnd.services.CargoService;
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

    @Autowired
    private CargoService cargoService;

    @PostMapping
    public ResponseEntity<?> salvarSetor(@RequestBody DtoSetor dtoSetor) throws Exception {
        setorService.verificaSetorExistente(dtoSetor);
        Setor setorSalvo = setorService.salvar(dtoSetor);
        cargoService.verificaCargoExistente(dtoSetor.getCargos());
        cargoService.salvar(dtoSetor.getCargos(), setorSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body("Setor criado com sucesso!");
    }

    @GetMapping
    public ResponseEntity<?> listarSetores(){
        return ResponseEntity.status(HttpStatus.OK).body(setorService.listarSetores());
    }
}