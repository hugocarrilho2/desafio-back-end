package com.example.testeBackEnd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Collection;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoCargo {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nome;

}
