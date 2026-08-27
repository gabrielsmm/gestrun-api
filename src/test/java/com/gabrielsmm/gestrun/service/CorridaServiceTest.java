package com.gabrielsmm.gestrun.service;

import com.gabrielsmm.gestrun.domain.Corrida;
import com.gabrielsmm.gestrun.domain.Usuario;
import com.gabrielsmm.gestrun.dto.CorridaInsertRequest;
import com.gabrielsmm.gestrun.exception.ValidacaoException;
import com.gabrielsmm.gestrun.mapper.CorridaMapper;
import com.gabrielsmm.gestrun.repository.CorridaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    private CorridaInsertRequest requestValido(String nome) {
        return new CorridaInsertRequest(
                nome,
                LocalDate.of(2026, 10, 10),
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
        corrida.setValorInscricao(new BigDecimal("50.00"));
        corrida.setInscricoesAbertura(LocalDateTime.of(2026, 8, 1, 8, 0));
        corrida.setInscricoesEncerramento(LocalDateTime.of(2026, 10, 1, 23, 59));
        corrida.setCapacidade(100);
        return corrida;
    }
}
