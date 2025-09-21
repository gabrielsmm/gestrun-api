package com.gabrielsmm.gestrun.dto;

import com.gabrielsmm.gestrun.domain.enums.StatusInscricao;

public record InscricaoUpdateRequest(
        StatusInscricao status,
        Integer numeroPeito
) {}
