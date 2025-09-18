package com.gabrielsmm.gestrun.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

    @NotBlank
    private String nome;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String senha;

}
