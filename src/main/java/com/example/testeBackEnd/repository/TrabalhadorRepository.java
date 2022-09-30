package com.example.testeBackEnd.repository;

import com.example.testeBackEnd.model.Cargo;
import com.example.testeBackEnd.model.Trabalhador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrabalhadorRepository extends JpaRepository<Trabalhador, Long> {

    boolean existsByCpf(String cpf);

}
