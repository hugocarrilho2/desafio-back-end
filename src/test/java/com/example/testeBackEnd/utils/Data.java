package com.example.testeBackEnd.utils;

import com.example.testeBackEnd.dto.DtoCargo;
import com.example.testeBackEnd.dto.DtoSetor;
import com.example.testeBackEnd.dto.DtoTrabalhador;
import com.example.testeBackEnd.model.Cargo;
import com.example.testeBackEnd.model.Setor;
import com.example.testeBackEnd.model.Trabalhador;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static Setor setor = Setor.builder().id(1L).nome("TI").cargos(new ArrayList<>()).build();

    public static Setor setor2 = Setor.builder().id(1L).nome("TI").cargos(new ArrayList<>()).build();

    public static DtoSetor dtoSetor = DtoSetor.builder().id(1L).nome("TI").cargos(new ArrayList<>()).build();

//    public static DtoSetor dtoSetor2 = DtoSetor.builder().id(1L).nome("TI2").cargos(new ArrayList<>()).build();

    public static Cargo cargo = Cargo.builder().id(1L).nome("Analista de Sistemas").setor(setor).build();

    public static DtoCargo dtoCargo = DtoCargo.builder().id(1L).nome("Analista de Sistemas").build();

    public static Trabalhador trabalhador = Trabalhador.builder().cpf("000.000.000-00").id(1L).nome("Joao")
            .cargo(cargo).setor(setor).build();

    public static DtoTrabalhador dtoTrabalhador = DtoTrabalhador.builder().id(1L).nome("Joao").cpf("000.000.000-00")
            .cargo(dtoCargo).setor(dtoSetor).build();

    public static DtoTrabalhador dtoTrabalhador2 = DtoTrabalhador.builder().id(1L).nome("Joao 2").cpf("000.000.000-00")
            .cargo(dtoCargo).setor(dtoSetor).build();

    public static DtoSetor setorParaCriar = DtoSetor.builder().nome("TI").cargos(List.of(DtoCargo.builder()
            .nome("Analista").build())).build();
    public static DtoSetor setorParaAtualizar = DtoSetor.builder().nome("TI2").cargos(List.of(DtoCargo.builder()
            .nome("Analista").build())).build();

    public static DtoTrabalhador trabalhadorParaCriar = DtoTrabalhador.builder().nome("Joao").cpf("000.000.000-00")
            .setor(DtoSetor.builder().id(1L).build()).cargo(DtoCargo.builder().id(1L).build()).build();

    public static DtoTrabalhador trabalhadorParaAtualizar = DtoTrabalhador.builder().nome("Joao2").cpf("000.000.000-00")
            .setor(DtoSetor.builder().id(1L).build()).cargo(DtoCargo.builder().id(1L).build()).build();
}
