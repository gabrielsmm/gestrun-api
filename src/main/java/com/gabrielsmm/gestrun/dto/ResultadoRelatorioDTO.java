package com.gabrielsmm.gestrun.dto;

import lombok.Data;

@Data
public class ResultadoRelatorioDTO {

    private Integer posicaoGeral;
    private Integer numeroPeito;
    private String nomeCorredor;
    private String tempo;

    public ResultadoRelatorioDTO(Integer posicaoGeral, Integer numeroPeito, String nomeCorredor, Object tempo) {
        this.posicaoGeral = posicaoGeral;
        this.numeroPeito = numeroPeito;
        this.nomeCorredor = nomeCorredor;
        this.tempo = tempo != null ? tempo.toString() : null;
    }

}
