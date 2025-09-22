package com.gabrielsmm.gestrun.dto;

import java.time.LocalTime;

public record ResultadoUpdateRequest(
        LocalTime tempo,
        Integer posicaoGeral
) {}
