package com.gabrielsmm.gestrun.controller;

import com.gabrielsmm.gestrun.domain.Resultado;
import com.gabrielsmm.gestrun.dto.PaginacaoResponse;
import com.gabrielsmm.gestrun.dto.ResultadoInsertRequest;
import com.gabrielsmm.gestrun.dto.ResultadoResponse;
import com.gabrielsmm.gestrun.dto.ResultadoUpdateRequest;
import com.gabrielsmm.gestrun.mapper.ResultadoMapper;
import com.gabrielsmm.gestrun.service.ResultadoService;
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

import java.util.List;

@RestController
@RequestMapping("/api/resultados")
@RequiredArgsConstructor
@Tag(name = "6. Resultados", description = "Gerenciamento de resultados de corridas")
@SecurityRequirement(name = "Bearer Authentication")
public class ResultadoController {

    private final ResultadoService resultadoService;
    private final ResultadoMapper resultadoMapper;

    @GetMapping("/corrida/{corridaId}")
    @Operation(summary = "Listar resultados por corrida de forma paginada (ordenados por tempo)")
    public PaginacaoResponse<ResultadoResponse> listarPorCorridaPaginadoOrdenadoPorTempo(
            @PathVariable Long corridaId,
            @RequestParam(value="pagina", defaultValue="0") Integer pagina,
            @RequestParam(value="registrosPorPagina", defaultValue="10") Integer registrosPorPagina,
            @RequestParam(value="ordem", defaultValue="id") String ordem,
            @RequestParam(value="direcao", defaultValue="ASC") String direcao,
            @RequestParam(value="filtro", required = false) String filtro
    ) {
        Page<Resultado> resultados = resultadoService.listarPorCorridaPaginadoOrdenadoPorTempo(corridaId, pagina, registrosPorPagina, ordem, direcao, filtro);
        return resultadoMapper.toPaginacaoResponse(resultados);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar resultado por ID")
    public ResponseEntity<ResultadoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(resultadoMapper.toResponse(resultadoService.buscarPorId(id)));
    }

    @PostMapping
    @Operation(summary = "Criar resultado")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<ResultadoResponse> criar(@Valid @RequestBody ResultadoInsertRequest request) {
        Resultado criado = resultadoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultadoMapper.toResponse(criado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar resultado")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<ResultadoResponse> atualizar(@PathVariable Long id,
                                                       @Valid @RequestBody ResultadoUpdateRequest request) {
        Resultado atualizado = resultadoService.atualizar(id, request);
        return ResponseEntity.ok(resultadoMapper.toResponse(atualizado));
    }

    @PutMapping("/lote")
    @Operation(summary = "Atualizar resultados em lote")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<Void> atualizarLote(@RequestBody List<ResultadoUpdateRequest> resultados) {
        resultadoService.atualizarLote(resultados);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar resultado")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        resultadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
