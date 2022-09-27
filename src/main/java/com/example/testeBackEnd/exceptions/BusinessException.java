package com.example.testeBackEnd.exceptions;

public class BusinessException extends RuntimeException
{
    public BusinessException(String message) {
        super(message);
    }
}
