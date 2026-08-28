package com.gabrielsmm.gestrun.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CorridaResponse(
        Long id,
        String nome,
        LocalDateTime dataHoraInicio,
        String local,
        BigDecimal distanciaKm,
        String regulamento,
        BigDecimal valorInscricao,
        LocalDateTime inscricoesAbertura,
        LocalDateTime inscricoesEncerramento,
        Integer capacidade,
        boolean publicada,
        String slug,
        Long organizadorId
) {}
