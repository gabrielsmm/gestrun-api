package com.gabrielsmm.gestrun.util;

import com.gabrielsmm.gestrun.domain.enums.Perfil;
import com.gabrielsmm.gestrun.security.UsuarioDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    private SecurityUtils() {
        throw new IllegalStateException("Classe utilitária");
    }

    public static boolean usuarioLogadoEhAdmin() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UsuarioDetails usuarioDetails) {
            return usuarioDetails.getPerfil() == Perfil.ADMIN;
        }

        return false;
    }

    public static String getEmailUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null) ? auth.getName() : null;
    }

}
