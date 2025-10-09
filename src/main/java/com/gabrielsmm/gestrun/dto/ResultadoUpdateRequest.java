package com.gabrielsmm.gestrun.dto;

import java.time.LocalTime;

public record ResultadoUpdateRequest(
        Long id,
        LocalTime tempo,
        Integer posicaoGeral
) {}
