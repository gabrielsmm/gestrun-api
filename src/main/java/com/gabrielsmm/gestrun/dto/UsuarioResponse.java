package com.gabrielsmm.gestrun.dto;

import com.gabrielsmm.gestrun.domain.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private Perfil perfil;
    private LocalDate dataCriacao;

}
