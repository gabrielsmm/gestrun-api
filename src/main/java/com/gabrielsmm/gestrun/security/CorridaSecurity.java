package com.gabrielsmm.gestrun.security;

import com.gabrielsmm.gestrun.domain.Corrida;
import com.gabrielsmm.gestrun.repository.CorridaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("corridaSecurity")
@RequiredArgsConstructor
public class CorridaSecurity {

    private final CorridaRepository corridaRepository;

    public boolean isOrganizador(Long corridaId, Long usuarioId) {
        return corridaRepository.findById(corridaId)
                .map(corrida -> corrida.getOrganizador().getId().equals(usuarioId))
                .orElse(false);
    }

    public boolean isOrganizador(Authentication authentication, Long corridaId) {
        UsuarioDetails usuario = (UsuarioDetails) authentication.getPrincipal();

        Corrida corrida = corridaRepository.findById(corridaId).orElse(null);

        if (corrida == null) return false;

        return corrida.getOrganizador().getId().equals(usuario.getId());
    }

}

