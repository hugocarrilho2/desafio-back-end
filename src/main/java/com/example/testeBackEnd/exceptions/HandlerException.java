package com.example.testeBackEnd.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@AllArgsConstructor
@RestControllerAdvice
public class HandlerException
{
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handlerBusinessException(BusinessException businessException)
    {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setTimestamp(OffsetDateTime.now());
        errorMessage.setMessage(businessException.getMessage());
        errorMessage.setStatus(status.value());

        return ResponseEntity.status(status).body(errorMessage);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handlerNotFoundException(NotFoundException notFoundException)
    {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setTimestamp(OffsetDateTime.now());
        errorMessage.setMessage(notFoundException.getMessage());
        errorMessage.setStatus(status.value());

        return ResponseEntity.status(status).body(errorMessage);
    }

}