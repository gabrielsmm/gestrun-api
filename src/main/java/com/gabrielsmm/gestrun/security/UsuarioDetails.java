package com.gabrielsmm.gestrun.security;

import com.gabrielsmm.gestrun.domain.Usuario;
import com.gabrielsmm.gestrun.domain.enums.Perfil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class UsuarioDetails implements UserDetails {

    private final Usuario usuario;

    public Long getId() {
        return usuario.getId();
    }

    public Perfil getPerfil() {
        return usuario.getPerfil();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + usuario.getPerfil().name())
        );
    }

    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

}
