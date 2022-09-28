package com.example.testeBackEnd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "setor")
public class Setor
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @OneToMany(mappedBy = "setor", cascade = CascadeType.ALL)
    private List<Cargo> cargos = new ArrayList<>();

    public void adicionarCargo(Cargo cargo){
        cargo.setSetor(this);
        this.getCargos().add(cargo);
    }
}