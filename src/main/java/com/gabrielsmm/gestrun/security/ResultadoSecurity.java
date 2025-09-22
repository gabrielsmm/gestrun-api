package com.gabrielsmm.gestrun.security;

import com.gabrielsmm.gestrun.repository.ResultadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("resultadoSecurity")
@RequiredArgsConstructor
public class ResultadoSecurity {

    private final ResultadoRepository resultadoRepository;

    public boolean podeGerenciar(Long resultadoId, UsuarioDetails usuarioDetails) {
        return resultadoRepository.findById(resultadoId)
                .map(r -> r.getInscricao().getCorrida().getOrganizador().getId().equals(usuarioDetails.getId()))
                .orElse(false);
    }

}
