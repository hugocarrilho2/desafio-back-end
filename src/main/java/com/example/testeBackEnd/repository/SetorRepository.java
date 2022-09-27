package com.example.testeBackEnd.repository;

import com.example.testeBackEnd.model.Cargo;
import com.example.testeBackEnd.model.Setor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SetorRepository extends JpaRepository<Setor, Long>
{
    boolean existsByNome(String nome);

}
