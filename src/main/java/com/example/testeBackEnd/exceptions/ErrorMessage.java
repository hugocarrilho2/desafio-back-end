package com.example.testeBackEnd.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorMessage
{
    private OffsetDateTime timestamp;
    private Integer status;
    private String message;
}
