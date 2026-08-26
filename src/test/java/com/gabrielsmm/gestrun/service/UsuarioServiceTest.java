package com.gabrielsmm.gestrun.service;

import com.gabrielsmm.gestrun.domain.Usuario;
import com.gabrielsmm.gestrun.domain.enums.Perfil;
import com.gabrielsmm.gestrun.dto.RegistroOrganizadorRequest;
import com.gabrielsmm.gestrun.exception.RecursoDuplicadoException;
import com.gabrielsmm.gestrun.exception.ValidacaoException;
import com.gabrielsmm.gestrun.mapper.UsuarioMapper;
import com.gabrielsmm.gestrun.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveRegistrarOrganizadorComPerfilOrganizadorESenhaCodificada() {
        RegistroOrganizadorRequest request = new RegistroOrganizadorRequest("Organizador", "org@exemplo.com", "senha-forte");
        Usuario usuario = new Usuario();
        usuario.setSenha(request.senha());

        when(usuarioRepository.existsByEmail(request.email())).thenReturn(false);
        when(usuarioMapper.toEntity(request)).thenReturn(usuario);
        when(passwordEncoder.encode(request.senha())).thenReturn("senha-codificada");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        usuarioService.registrarOrganizador(request);

        assertThat(usuario.getPerfil()).isEqualTo(Perfil.ORGANIZADOR);
        assertThat(usuario.getSenha()).isEqualTo("senha-codificada");
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void deveRecusarRegistroComEmailJaCadastrado() {
        RegistroOrganizadorRequest request = new RegistroOrganizadorRequest("Organizador", "org@exemplo.com", "senha-forte");
        when(usuarioRepository.existsByEmail(request.email())).thenReturn(true);

        assertThatThrownBy(() -> usuarioService.registrarOrganizador(request))
                .isInstanceOf(RecursoDuplicadoException.class);

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void naoDeveAlterarAdministradorExistenteNoBootstrap() {
        Usuario administrador = new Usuario();
        administrador.setPerfil(Perfil.ADMIN);
        when(usuarioRepository.findByEmail("admin@exemplo.com")).thenReturn(Optional.of(administrador));

        usuarioService.criarAdministradorBootstrap("Administrador", "admin@exemplo.com", "senha-forte");

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveFalharQuandoEmailDoBootstrapPertencerAOutroPerfil() {
        Usuario organizador = new Usuario();
        organizador.setPerfil(Perfil.ORGANIZADOR);
        when(usuarioRepository.findByEmail("admin@exemplo.com")).thenReturn(Optional.of(organizador));

        assertThatThrownBy(() -> usuarioService.criarAdministradorBootstrap("Administrador", "admin@exemplo.com", "senha-forte"))
                .isInstanceOf(ValidacaoException.class);

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void deveCriarAdministradorQuandoNaoExistirNoBootstrap() {
        when(usuarioRepository.findByEmail("admin@exemplo.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("senha-forte")).thenReturn("senha-codificada");

        usuarioService.criarAdministradorBootstrap("Administrador", "admin@exemplo.com", "senha-forte");

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        assertThat(captor.getValue().getPerfil()).isEqualTo(Perfil.ADMIN);
        assertThat(captor.getValue().getSenha()).isEqualTo("senha-codificada");
    }
}
