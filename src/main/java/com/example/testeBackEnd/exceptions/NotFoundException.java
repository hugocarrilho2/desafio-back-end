package com.example.testeBackEnd.exceptions;

public class NotFoundException extends RuntimeException
{
    public NotFoundException(String message) {
        super(message);
    }
}
