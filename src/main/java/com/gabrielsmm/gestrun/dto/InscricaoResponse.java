package com.gabrielsmm.gestrun.dto;

import com.gabrielsmm.gestrun.domain.enums.SexoInscricao;
import com.gabrielsmm.gestrun.domain.enums.StatusInscricao;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record InscricaoResponse(
        Long id,
        Long corridaId,
        String nomeCorredor,
        String documento,
        LocalDate dataNascimento,
        SexoInscricao sexo,
        String email,
        String telefone,
        StatusInscricao status,
        Integer numeroPeito,
        LocalDateTime dataCriacao
) {}
