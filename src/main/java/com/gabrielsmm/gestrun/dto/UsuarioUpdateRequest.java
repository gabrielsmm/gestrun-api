package com.gabrielsmm.gestrun.dto;

import com.gabrielsmm.gestrun.domain.enums.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateRequest(
        @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres")
        String nome,

        @Email(message = "Formato de e-mail inválido")
        String email,

        @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
        String senha,

        Perfil perfil
) {}
