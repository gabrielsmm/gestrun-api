package com.gabrielsmm.gestrun.config;

import com.gabrielsmm.gestrun.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StringUtils;

@Configuration
@Profile("bootstrap-admin")
@RequiredArgsConstructor
public class BootstrapAdminConfig {

    private final UsuarioService usuarioService;

    @Bean
    ApplicationRunner bootstrapAdminRunner(
            @Value("${BOOTSTRAP_ADMIN_NOME:}") String nome,
            @Value("${BOOTSTRAP_ADMIN_EMAIL:}") String email,
            @Value("${BOOTSTRAP_ADMIN_SENHA:}") String senha
    ) {
        return arguments -> {
            if (!StringUtils.hasText(nome) || !StringUtils.hasText(email) || !StringUtils.hasText(senha)) {
                throw new IllegalStateException("Defina BOOTSTRAP_ADMIN_NOME, BOOTSTRAP_ADMIN_EMAIL e BOOTSTRAP_ADMIN_SENHA para usar o perfil bootstrap-admin");
            }

            usuarioService.criarAdministradorBootstrap(nome, email, senha);
        };
    }
}
