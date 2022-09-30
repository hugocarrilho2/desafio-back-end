package com.example.testeBackEnd.model;

import com.example.testeBackEnd.dto.DtoSetor;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "setor")
public class Setor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @OneToMany(mappedBy = "setor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cargo> cargos = new ArrayList<>();

    public static DtoSetor converterParaDto(Setor setor) {
        DtoSetor dtoSetor = new DtoSetor();
        BeanUtils.copyProperties(setor, dtoSetor);

        return dtoSetor;
    }

    public static Setor converterParaSetor(DtoSetor dtoSetor) {
        Setor setor = new Setor();
        BeanUtils.copyProperties(dtoSetor, setor);
        setor.setId(null);

        return setor;
    }

    public void adicionarCargo(Cargo novoCargo) {
        novoCargo.setSetor(this);
        this.getCargos().add(novoCargo);
    }

    public void removerCargo(Cargo cargo) {
        this.getCargos().remove(cargo);
    }

}