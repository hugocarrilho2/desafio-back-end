package com.example.testeBackEnd.services.impl;

import com.example.testeBackEnd.repository.CargoRepository;
import com.example.testeBackEnd.services.CargoService;
import com.example.testeBackEnd.services.SetorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargoServiceImpl implements CargoService
{
    @Autowired
    private CargoRepository cargoRepository;
}
