package com.gabrielsmm.gestrun.controller;

import com.gabrielsmm.gestrun.domain.Inscricao;
import com.gabrielsmm.gestrun.dto.InscricaoInsertRequest;
import com.gabrielsmm.gestrun.dto.InscricaoResponse;
import com.gabrielsmm.gestrun.dto.InscricaoUpdateRequest;
import com.gabrielsmm.gestrun.mapper.InscricaoMapper;
import com.gabrielsmm.gestrun.service.InscricaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/corridas/{corridaId}/inscricoes")
@RequiredArgsConstructor
@Tag(name = "5. Inscrições", description = "Gerenciamento de inscrições")
@SecurityRequirement(name = "Bearer Authentication")
public class InscricaoController {

    private final InscricaoService inscricaoService;
    private final InscricaoMapper inscricaoMapper;

    @GetMapping
    @Operation(summary = "Listar inscrições de uma corrida")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public List<InscricaoResponse> listar(@PathVariable Long corridaId) {
        return inscricaoService.listarPorCorrida(corridaId).stream()
                .map(inscricaoMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar inscrição por ID")
    @PermitAll
    public ResponseEntity<InscricaoResponse> buscar(@PathVariable Long corridaId,
                                                    @PathVariable Long id,
                                                    @RequestParam String documentoOuEmail) {
        Inscricao inscricao = inscricaoService.buscarPublico(corridaId, id, documentoOuEmail);
        return ResponseEntity.ok(inscricaoMapper.toResponse(inscricao));
    }

    @PostMapping
    @Operation(summary = "Criar inscrição em uma corrida")
    @PermitAll
    public ResponseEntity<InscricaoResponse> criar(@PathVariable Long corridaId,
                                                   @Valid @RequestBody InscricaoInsertRequest request) {
        Inscricao criada = inscricaoService.criar(corridaId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(inscricaoMapper.toResponse(criada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar inscrição")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<InscricaoResponse> atualizar(@PathVariable Long corridaId,
                                                       @PathVariable Long id,
                                                       @Valid @RequestBody InscricaoUpdateRequest request) {
        Inscricao atualizada = inscricaoService.atualizar(id, request);
        return ResponseEntity.ok(inscricaoMapper.toResponse(atualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar inscrição")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<Void> deletar(@PathVariable Long corridaId,
                                        @PathVariable Long id) {
        inscricaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
