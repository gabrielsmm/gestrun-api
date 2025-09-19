package com.gabrielsmm.gestrun.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

    @NotBlank
    @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres")
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    private String senha;

}
