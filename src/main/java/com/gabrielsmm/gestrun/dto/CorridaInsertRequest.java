package com.gabrielsmm.gestrun.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CorridaInsertRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotNull(message = "A data é obrigatória")
        LocalDate data,

        @NotBlank(message = "O local é obrigatório")
        String local,

        @DecimalMin(value = "0.1", message = "A distância deve ser maior que zero")
        BigDecimal distanciaKm,

        String regulamento
) {}
