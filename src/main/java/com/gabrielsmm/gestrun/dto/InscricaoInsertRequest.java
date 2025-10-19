package com.gabrielsmm.gestrun.dto;

import com.gabrielsmm.gestrun.domain.enums.SexoInscricao;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record InscricaoInsertRequest(
        @NotNull(message = "O ID da corrida é obrigatório")
        Long corridaId,

        @NotBlank(message = "O nome do corredor é obrigatório")
        String nomeCorredor,

        String documento,

        @NotNull(message = "A data de nascimento é obrigatória")
        @Past(message = "Data de nascimento deve ser uma data no passado")
        LocalDate dataNascimento,

        @NotNull(message = "O sexo é obrigatório")
        SexoInscricao sexo,

        @Email(message = "Formato de e-mail inválido")
        String email,

        String telefone
) {}
