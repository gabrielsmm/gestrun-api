package com.gabrielsmm.gestrun.dto;

import com.gabrielsmm.gestrun.domain.enums.SexoInscricao;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InscricaoInsertRequest(
        @NotBlank String nomeCorredor,
        String documento,
        @NotNull LocalDate dataNascimento,
        @NotNull SexoInscricao sexo,
        @Email String email,
        String telefone
) {}
