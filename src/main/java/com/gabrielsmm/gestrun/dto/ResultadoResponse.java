package com.gabrielsmm.gestrun.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record ResultadoResponse(
        Long id,
        Long inscricaoId,
        String nomeCorredor,
        Integer numeroPeito,
        LocalTime tempo,
        Integer posicaoGeral,
        LocalDateTime dataCriacao
) {}
