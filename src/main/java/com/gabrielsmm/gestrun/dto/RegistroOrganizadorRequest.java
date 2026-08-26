package com.gabrielsmm.gestrun.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistroOrganizadorRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
        String senha
) {
}
