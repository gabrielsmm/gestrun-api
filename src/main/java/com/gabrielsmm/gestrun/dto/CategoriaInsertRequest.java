package com.gabrielsmm.gestrun.dto;

import com.gabrielsmm.gestrun.domain.enums.SexoCategoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoriaInsertRequest(
        @NotNull(message = "O ID da corrida é obrigatório")
        Long corridaId,

        @NotBlank(message = "O nome é obrigatório")
        String nome,

        Integer idadeMin,

        Integer idadeMax,

        @NotNull(message = "O sexo é obrigatório")
        SexoCategoria sexo
) {}
