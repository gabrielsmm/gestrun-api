package com.gabrielsmm.gestrun.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CorridaUpdateRequest(
        String nome,
        LocalDate data,
        String local,
        BigDecimal distanciaKm,
        String regulamento
) {}
