package com.gabrielsmm.gestrun.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CorridaUpdateRequest(
        String nome,
        LocalDate data,
        String local,
        BigDecimal distanciaKm,
        String regulamento,
        @DecimalMin(value = "0.0", message = "O valor da inscrição não pode ser negativo")
        BigDecimal valorInscricao,
        LocalDateTime inscricoesAbertura,
        LocalDateTime inscricoesEncerramento,
        @Min(value = 0, message = "A capacidade não pode ser negativa")
        Integer capacidade
) {}
