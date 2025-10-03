package com.gabrielsmm.gestrun.controller;

import com.gabrielsmm.gestrun.domain.Inscricao;
import com.gabrielsmm.gestrun.dto.InscricaoInsertRequest;
import com.gabrielsmm.gestrun.dto.InscricaoResponse;
import com.gabrielsmm.gestrun.dto.InscricaoUpdateRequest;
import com.gabrielsmm.gestrun.dto.PaginacaoResponse;
import com.gabrielsmm.gestrun.mapper.InscricaoMapper;
import com.gabrielsmm.gestrun.service.InscricaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscricoes")
@RequiredArgsConstructor
@Tag(name = "5. Inscrições", description = "Gerenciamento de inscrições")
@SecurityRequirement(name = "Bearer Authentication")
public class InscricaoController {

    private final InscricaoService inscricaoService;
    private final InscricaoMapper inscricaoMapper;

    @GetMapping("/corrida/{corridaId}")
    @Operation(summary = "Listar inscrições de uma corrida de forma paginada")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public PaginacaoResponse<InscricaoResponse> listarPorCorridaPaginado(
            @PathVariable Long corridaId,
            @RequestParam(value="pagina", defaultValue="0") Integer pagina,
            @RequestParam(value="registrosPorPagina", defaultValue="10") Integer registrosPorPagina,
            @RequestParam(value="ordem", defaultValue="id") String ordem,
            @RequestParam(value="direcao", defaultValue="ASC") String direcao,
            @RequestParam(value="filtro", required = false) String filtro
    ) {
        Page<Inscricao> inscricoes = inscricaoService.listarPorCorridaPaginado(corridaId, pagina, registrosPorPagina, ordem, direcao, filtro);
        return inscricaoMapper.toPaginacaoResponse(inscricoes);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar inscrição por ID")
    public ResponseEntity<InscricaoResponse> buscar(@PathVariable Long id,
                                                    @RequestParam String documentoOuEmail) {
        Inscricao inscricao = inscricaoService.buscarPublico(id, documentoOuEmail);
        return ResponseEntity.ok(inscricaoMapper.toResponse(inscricao));
    }

    @PostMapping
    @Operation(summary = "Criar inscrição em uma corrida")
    public ResponseEntity<InscricaoResponse> criar(@Valid @RequestBody InscricaoInsertRequest request) {
        Inscricao criada = inscricaoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(inscricaoMapper.toResponse(criada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar inscrição")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<InscricaoResponse> atualizar(@PathVariable Long id,
                                                       @Valid @RequestBody InscricaoUpdateRequest request) {
        Inscricao atualizada = inscricaoService.atualizar(id, request);
        return ResponseEntity.ok(inscricaoMapper.toResponse(atualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar inscrição")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        inscricaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
