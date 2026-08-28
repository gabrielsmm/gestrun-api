package com.gabrielsmm.gestrun.service;

import com.gabrielsmm.gestrun.domain.Corrida;
import com.gabrielsmm.gestrun.domain.Usuario;
import com.gabrielsmm.gestrun.domain.enums.Perfil;
import com.gabrielsmm.gestrun.dto.CorridaInsertRequest;
import com.gabrielsmm.gestrun.exception.ValidacaoException;
import com.gabrielsmm.gestrun.mapper.CorridaMapper;
import com.gabrielsmm.gestrun.repository.CorridaRepository;
import com.gabrielsmm.gestrun.security.UsuarioDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CorridaServiceTest {

    @Mock
    private CorridaRepository corridaRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private CorridaMapper corridaMapper;

    @InjectMocks
    private CorridaService corridaService;

    @AfterEach
    void limparContextoDeSeguranca() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void deveCriarCorridaComoRascunhoComSlugNormalizado() {
        CorridaInsertRequest request = requestValido("Corrida São João 2026");
        Corrida corrida = corridaValida();
        Usuario organizador = new Usuario();
        organizador.setId(7L);

        when(usuarioService.buscarPorId(7L)).thenReturn(organizador);
        when(corridaMapper.toEntity(request)).thenReturn(corrida);
        when(corridaRepository.existsBySlug("corrida-sao-joao-2026")).thenReturn(false);
        when(corridaRepository.save(corrida)).thenReturn(corrida);

        corridaService.criar(7L, request);

        assertThat(corrida.getOrganizador()).isEqualTo(organizador);
        assertThat(corrida.isPublicada()).isFalse();
        assertThat(corrida.getSlug()).isEqualTo("corrida-sao-joao-2026");
        verify(corridaRepository).save(corrida);
    }

    @Test
    void deveGerarSlugComSufixoQuandoJaExistir() {
        CorridaInsertRequest request = requestValido("Corrida Teste");
        Corrida corrida = corridaValida();
        Usuario organizador = new Usuario();

        when(usuarioService.buscarPorId(1L)).thenReturn(organizador);
        when(corridaMapper.toEntity(request)).thenReturn(corrida);
        when(corridaRepository.existsBySlug("corrida-teste")).thenReturn(true);
        when(corridaRepository.existsBySlug("corrida-teste-2")).thenReturn(false);
        when(corridaRepository.save(corrida)).thenReturn(corrida);

        corridaService.criar(1L, request);

        assertThat(corrida.getSlug()).isEqualTo("corrida-teste-2");
    }

    @Test
    void deveRecusarPeriodoDeInscricoesInvalido() {
        CorridaInsertRequest request = requestValido("Corrida Teste");
        Corrida corrida = corridaValida();
        corrida.setInscricoesEncerramento(corrida.getInscricoesAbertura());
        when(usuarioService.buscarPorId(1L)).thenReturn(new Usuario());
        when(corridaMapper.toEntity(request)).thenReturn(corrida);
        when(corridaRepository.existsBySlug(any())).thenReturn(false);

        assertThatThrownBy(() -> corridaService.criar(1L, request))
                .isInstanceOf(ValidacaoException.class)
                .hasMessageContaining("encerramento");

        verify(corridaRepository, never()).save(any());
    }

    @Test
    void deveRecusarCapacidadeNegativa() {
        CorridaInsertRequest request = requestValido("Corrida Teste");
        Corrida corrida = corridaValida();
        corrida.setCapacidade(-1);
        when(usuarioService.buscarPorId(1L)).thenReturn(new Usuario());
        when(corridaMapper.toEntity(request)).thenReturn(corrida);
        when(corridaRepository.existsBySlug(any())).thenReturn(false);

        assertThatThrownBy(() -> corridaService.criar(1L, request))
                .isInstanceOf(ValidacaoException.class)
                .hasMessageContaining("capacidade");

        verify(corridaRepository, never()).save(any());
    }

    @Test
    void deveRecusarEncerramentoIgualAoInicioDaCorrida() {
        CorridaInsertRequest request = requestValido("Corrida Teste");
        Corrida corrida = corridaValida();
        corrida.setInscricoesEncerramento(corrida.getDataHoraInicio());
        when(usuarioService.buscarPorId(1L)).thenReturn(new Usuario());
        when(corridaMapper.toEntity(request)).thenReturn(corrida);
        when(corridaRepository.existsBySlug(any())).thenReturn(false);

        assertThatThrownBy(() -> corridaService.criar(1L, request))
                .isInstanceOf(ValidacaoException.class)
                .hasMessageContaining("anterior ao início");

        verify(corridaRepository, never()).save(any());
    }

    @Test
    void devePermitirPublicacaoPeloOrganizadorResponsavel() {
        Usuario organizador = usuario(7L, Perfil.ORGANIZADOR);
        Corrida corrida = corridaValida();
        corrida.setId(10L);
        corrida.setOrganizador(organizador);
        autenticar(organizador);
        when(corridaRepository.findById(10L)).thenReturn(Optional.of(corrida));
        when(corridaRepository.save(corrida)).thenReturn(corrida);

        Corrida publicada = corridaService.alterarPublicacao(10L, true);

        assertThat(publicada.isPublicada()).isTrue();
        verify(corridaRepository).save(corrida);
    }

    private CorridaInsertRequest requestValido(String nome) {
        return new CorridaInsertRequest(
                nome,
                LocalDateTime.of(2026, 10, 10, 7, 0),
                "Parque Municipal",
                new BigDecimal("5.00"),
                "Regulamento",
                new BigDecimal("50.00"),
                LocalDateTime.of(2026, 8, 1, 8, 0),
                LocalDateTime.of(2026, 10, 1, 23, 59),
                100
        );
    }

    private Corrida corridaValida() {
        Corrida corrida = new Corrida();
        corrida.setNome("Corrida Teste");
        corrida.setLocal("Parque Municipal");
        corrida.setValorInscricao(new BigDecimal("50.00"));
        corrida.setDataHoraInicio(LocalDateTime.of(2026, 10, 10, 7, 0));
        corrida.setInscricoesAbertura(LocalDateTime.of(2026, 8, 1, 8, 0));
        corrida.setInscricoesEncerramento(LocalDateTime.of(2026, 10, 1, 23, 59));
        corrida.setCapacidade(100);
        return corrida;
    }

    private Usuario usuario(Long id, Perfil perfil) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail("organizador@exemplo.com");
        usuario.setPerfil(perfil);
        return usuario;
    }

    private void autenticar(Usuario usuario) {
        UsuarioDetails usuarioDetails = new UsuarioDetails(usuario);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(usuarioDetails, null, usuarioDetails.getAuthorities())
        );
    }
}
