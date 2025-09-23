package com.gabrielsmm.gestrun.util;

import com.gabrielsmm.gestrun.domain.enums.Perfil;
import com.gabrielsmm.gestrun.security.UsuarioDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    private SecurityUtils() {
        throw new IllegalStateException("Classe utilitária");
    }

    public static boolean usuarioLogadoEhAdmin() {
        return getUsuarioLogado().getPerfil() == Perfil.ADMIN;
    }

    public static String getEmailUsuarioLogado() {
        return getUsuarioLogado().getUsername(); // UserDetails.getUsername() = email
    }

    public static Long getUsuarioIdLogado() {
        return getUsuarioLogado().getId();
    }

    public static UsuarioDetails getUsuarioLogado() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UsuarioDetails usuarioDetails)) {
            throw new IllegalStateException("Nenhum usuário autenticado encontrado");
        }

        return usuarioDetails;
    }

}
