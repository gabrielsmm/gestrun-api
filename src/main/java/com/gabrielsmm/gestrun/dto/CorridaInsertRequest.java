package com.gabrielsmm.gestrun.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CorridaInsertRequest(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotNull(message = "A data é obrigatória")
        LocalDate data,

        @NotBlank(message = "O local é obrigatório")
        String local,

        @DecimalMin(value = "0.1", message = "A distância deve ser maior que zero")
        BigDecimal distanciaKm,

        String regulamento,

        @NotNull(message = "O valor da inscrição é obrigatório")
        @DecimalMin(value = "0.0", message = "O valor da inscrição não pode ser negativo")
        BigDecimal valorInscricao,

        @NotNull(message = "A abertura das inscrições é obrigatória")
        LocalDateTime inscricoesAbertura,

        @NotNull(message = "O encerramento das inscrições é obrigatório")
        LocalDateTime inscricoesEncerramento,

        @NotNull(message = "A capacidade é obrigatória")
        @Min(value = 0, message = "A capacidade não pode ser negativa")
        Integer capacidade
) {}
