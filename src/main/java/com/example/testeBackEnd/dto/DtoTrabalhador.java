package com.example.testeBackEnd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoTrabalhador {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nome;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cpf;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DtoSetor setor;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DtoCargo cargo;

}
