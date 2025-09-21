package com.gabrielsmm.gestrun.security;

import com.gabrielsmm.gestrun.repository.CorridaRepository;
import lombok.RequiredArgsConstructor;
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

}

