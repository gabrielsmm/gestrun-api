package com.gabrielsmm.gestrun.dto;

import com.gabrielsmm.gestrun.domain.enums.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdateRequest {

    @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres")
    private String nome;

    @Email(message = "Formato de e-mail inválido")
    private String email;

    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    private String senha;

    private Perfil perfil;

}
