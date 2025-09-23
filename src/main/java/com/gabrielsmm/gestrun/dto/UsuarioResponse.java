package com.gabrielsmm.gestrun.dto;

import com.gabrielsmm.gestrun.domain.enums.Perfil;

import java.time.LocalDate;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        Perfil perfil,
        LocalDate dataCriacao
) {}
