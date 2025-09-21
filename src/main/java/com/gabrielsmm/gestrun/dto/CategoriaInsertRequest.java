package com.gabrielsmm.gestrun.dto;

import com.gabrielsmm.gestrun.domain.enums.SexoCategoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoriaInsertRequest(
        @NotBlank String nome,
        Integer idadeMin,
        Integer idadeMax,
        @NotNull SexoCategoria sexo
) {}
