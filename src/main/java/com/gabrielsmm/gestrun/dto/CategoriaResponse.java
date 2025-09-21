package com.gabrielsmm.gestrun.dto;

import com.gabrielsmm.gestrun.domain.enums.SexoCategoria;

public record CategoriaResponse(
        Long id,
        String nome,
        Integer idadeMin,
        Integer idadeMax,
        SexoCategoria sexo,
        Long corridaId
) {}
