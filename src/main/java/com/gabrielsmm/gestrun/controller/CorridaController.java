package com.gabrielsmm.gestrun.controller;

import com.gabrielsmm.gestrun.domain.Corrida;
import com.gabrielsmm.gestrun.dto.CorridaInsertRequest;
import com.gabrielsmm.gestrun.dto.CorridaResponse;
import com.gabrielsmm.gestrun.dto.CorridaUpdateRequest;
import com.gabrielsmm.gestrun.mapper.CorridaMapper;
import com.gabrielsmm.gestrun.security.UsuarioDetails;
import com.gabrielsmm.gestrun.service.CorridaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/corridas")
@RequiredArgsConstructor
@Tag(name = "3. Corridas", description = "Gerenciamento de corridas")
@SecurityRequirement(name = "Bearer Authentication")
public class CorridaController {

    private final CorridaService corridaService;
    private final CorridaMapper corridaMapper;

    @GetMapping
    @Operation(summary = "Listar todas as corridas")
    public List<CorridaResponse> listar() {
        return corridaService.listar().stream()
                .map(corridaMapper::toResponse)
                .toList();
    }

    @GetMapping("/organizador")
    @Operation(summary = "Listar corridas do organizador autenticado")
    @PreAuthorize("hasRole('ORGANIZADOR')")
    public List<CorridaResponse> listarPorOrganizador(@AuthenticationPrincipal UsuarioDetails usuarioDetails) {
        return corridaService.listarPorOrganizador(usuarioDetails.getId()).stream()
                .map(corridaMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar corrida por ID")
    public ResponseEntity<CorridaResponse> buscar(@PathVariable Long id) {
        Corrida corrida = corridaService.buscarPorId(id);
        return ResponseEntity.ok(corridaMapper.toResponse(corrida));
    }

    @PostMapping
    @Operation(summary = "Criar corrida")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<CorridaResponse> criar(@RequestBody @Valid CorridaInsertRequest request,
                                                 @AuthenticationPrincipal UsuarioDetails usuarioDetails) {
        Corrida criada = corridaService.criar(usuarioDetails.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(corridaMapper.toResponse(criada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar corrida")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<CorridaResponse> atualizar(@PathVariable Long id,
                                                     @RequestBody @Valid CorridaUpdateRequest request) {
        Corrida corridaAtualizada = corridaService.atualizar(id, request);
        return ResponseEntity.ok(corridaMapper.toResponse(corridaAtualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar corrida")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        corridaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
