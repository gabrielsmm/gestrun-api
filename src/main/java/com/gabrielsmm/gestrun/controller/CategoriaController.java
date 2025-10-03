package com.gabrielsmm.gestrun.controller;

import com.gabrielsmm.gestrun.domain.Categoria;
import com.gabrielsmm.gestrun.dto.CategoriaInsertRequest;
import com.gabrielsmm.gestrun.dto.CategoriaResponse;
import com.gabrielsmm.gestrun.dto.CategoriaUpdateRequest;
import com.gabrielsmm.gestrun.dto.PaginacaoResponse;
import com.gabrielsmm.gestrun.mapper.CategoriaMapper;
import com.gabrielsmm.gestrun.service.CategoriaService;
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
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "4. Categorias", description = "Gerenciamento de categorias")
@SecurityRequirement(name = "Bearer Authentication")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    @GetMapping("/corrida/{corridaId}")
    @Operation(summary = "Listar categorias de uma corrida de forma paginada")
    public PaginacaoResponse<CategoriaResponse> listarPorCorridaPaginado(
            @PathVariable Long corridaId,
            @RequestParam(value="pagina", defaultValue="0") Integer pagina,
            @RequestParam(value="registrosPorPagina", defaultValue="10") Integer registrosPorPagina,
            @RequestParam(value="ordem", defaultValue="id") String ordem,
            @RequestParam(value="direcao", defaultValue="ASC") String direcao,
            @RequestParam(value="filtro", required = false) String filtro
    ) {
        Page<Categoria> categorias = categoriaService.listarPorCorridaPaginado(corridaId, pagina, registrosPorPagina, ordem, direcao, filtro);
        return categoriaMapper.toPaginacaoResponse(categorias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID")
    public ResponseEntity<CategoriaResponse> buscar(@PathVariable Long id) {
        Categoria categoria = categoriaService.buscarPorId(id);
        return ResponseEntity.ok(categoriaMapper.toResponse(categoria));
    }

    @PostMapping
    @Operation(summary = "Criar categoria em uma corrida")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<CategoriaResponse> criar(@Valid @RequestBody CategoriaInsertRequest request) {
        Categoria criada = categoriaService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaMapper.toResponse(criada));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<CategoriaResponse> atualizar(@PathVariable Long id,
                                                       @Valid @RequestBody CategoriaUpdateRequest request) {
        Categoria atualizada = categoriaService.atualizar(id, request);
        return ResponseEntity.ok(categoriaMapper.toResponse(atualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar categoria")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
