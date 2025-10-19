package com.gabrielsmm.gestrun.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultadoRelatorioDTO {

    private Integer posicao;
    private Integer numeroPeito;
    private String nomeCorredor;
    private Integer idade;
    private String sexo;
    private String tempo;

}
