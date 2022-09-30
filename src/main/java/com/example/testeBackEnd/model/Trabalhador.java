package com.example.testeBackEnd.model;

import com.example.testeBackEnd.dto.DtoTrabalhador;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "trabalhador")
public class Trabalhador
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column
    private String cpf;

    @OneToOne
    private Cargo cargo;

    @OneToOne
    private Setor setor;

    public static DtoTrabalhador converterParaDto(Trabalhador trabalhador) {
        DtoTrabalhador dtoTrabalhador = new DtoTrabalhador();
        BeanUtils.copyProperties(trabalhador, dtoTrabalhador);

        return dtoTrabalhador;
    }

    public static Trabalhador converterParaTrabalhador(DtoTrabalhador dtoTrabalhador) {
        Trabalhador trabalhador = new Trabalhador();
        BeanUtils.copyProperties(dtoTrabalhador, trabalhador);

        return trabalhador;

    }

}
