package com.gabrielsmm.gestrun.dto;

import com.gabrielsmm.gestrun.domain.enums.SexoCategoria;

public record CategoriaUpdateRequest(
        String nome,
        Integer idadeMin,
        Integer idadeMax,
        SexoCategoria sexo
) {}
