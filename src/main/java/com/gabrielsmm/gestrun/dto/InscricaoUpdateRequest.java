package com.gabrielsmm.gestrun.dto;

import com.gabrielsmm.gestrun.domain.enums.SexoInscricao;
import com.gabrielsmm.gestrun.domain.enums.StatusInscricao;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record InscricaoUpdateRequest(
        StatusInscricao status,

        @Positive(message = "O número de peito deve ser maior que zero")
        Integer numeroPeito,

        String nomeCorredor,
        String documento,

        @Past(message = "Data de nascimento deve ser uma data no passado")
        LocalDate dataNascimento,

        SexoInscricao sexo,
        String email,
        String telefone
) {}
