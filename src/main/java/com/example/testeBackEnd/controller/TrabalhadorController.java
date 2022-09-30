package com.example.testeBackEnd.controller;

import com.example.testeBackEnd.dto.DtoTrabalhador;
import com.example.testeBackEnd.services.TrabalhadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/trabalhadores")
public class TrabalhadorController {

    @Autowired
    private TrabalhadorService trabalhadorService;

    @PostMapping
    public ResponseEntity<?> salvarTrabalhador(@RequestBody DtoTrabalhador dtoTrabalhador) {
        trabalhadorService.verificaCpfExistente(dtoTrabalhador.getCpf());
        trabalhadorService.salvarTrabalhador(dtoTrabalhador);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/{idTrabalhador}")
    public ResponseEntity<?> buscarTrabalhador(@PathVariable("idTrabalhador") Long idTrabalhador) {
        DtoTrabalhador trabalhador = trabalhadorService.buscarDetalhesTrabalhador(idTrabalhador);

        return ResponseEntity.status(HttpStatus.OK).body(trabalhador);
    }

    @GetMapping
    public ResponseEntity<?> listarTrabalhadores() {
        return ResponseEntity.status(HttpStatus.OK).body(trabalhadorService.listarTrabalhadores());
    }

    @PutMapping(value = "/{idTrabalhador}")
    public ResponseEntity<?> atualizarTrabalhador(@PathVariable("idTrabalhador") Long idTrabalhador,
                                                  @RequestBody DtoTrabalhador dtoTrabalhador) {
        trabalhadorService.atualizarTrabalhador(dtoTrabalhador, idTrabalhador);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{idTrabalhador}")
    public ResponseEntity<?> excluirTrabalhador(@PathVariable("idTrabalhador") Long idTrabalhador) {
        trabalhadorService.excluirTrabalhador(idTrabalhador);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
