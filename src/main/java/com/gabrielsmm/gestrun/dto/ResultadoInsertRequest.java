package com.gabrielsmm.gestrun.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record ResultadoInsertRequest(
        @NotNull(message = "A inscrição é obrigatória")
        Long inscricaoId,

        @NotNull(message = "O tempo é obrigatório")
        LocalTime tempo,

        @NotNull(message = "A posição geral é obrigatória")
        Integer posicaoGeral
) {}
