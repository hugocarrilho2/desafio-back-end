package com.example.testeBackEnd.model;

import com.example.testeBackEnd.dto.DtoCargo;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cargo")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToOne
    private Setor setor;

    public static DtoCargo converterParaDto(Cargo cargo) {
        DtoCargo dtoCargo = new DtoCargo();
        BeanUtils.copyProperties(cargo, dtoCargo);
        return dtoCargo;
    }

    public static Cargo converterParaCargo(DtoCargo dtoCargo) {
        Cargo cargo = new Cargo();
        BeanUtils.copyProperties(dtoCargo, cargo);

        return cargo;
    }
}
