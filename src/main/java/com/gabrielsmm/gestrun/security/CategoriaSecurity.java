package com.gabrielsmm.gestrun.security;

import com.gabrielsmm.gestrun.domain.Categoria;
import com.gabrielsmm.gestrun.domain.Corrida;
import com.gabrielsmm.gestrun.repository.CategoriaRepository;
import com.gabrielsmm.gestrun.repository.CorridaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("categoriaSecurity")
@RequiredArgsConstructor
public class CategoriaSecurity {

    private final CategoriaRepository categoriaRepository;
    private final CorridaRepository corridaRepository;

    public boolean isOrganizador(Authentication authentication, Long categoriaId) {
        UsuarioDetails usuario = (UsuarioDetails) authentication.getPrincipal();

        Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);

        if (categoria == null) return false;

        Corrida corrida = categoria.getCorrida();
        return corrida.getOrganizador().getId().equals(usuario.getId());
    }

    public boolean isOrganizadorDaCorrida(Authentication authentication, Long corridaId) {
        UsuarioDetails usuario = (UsuarioDetails) authentication.getPrincipal();

        Corrida corrida = corridaRepository.findById(corridaId).orElse(null);

        if (corrida == null) return false;

        return corrida.getOrganizador().getId().equals(usuario.getId());
    }

}
