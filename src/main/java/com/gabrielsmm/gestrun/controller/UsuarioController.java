package com.gabrielsmm.gestrun.controller;

import com.gabrielsmm.gestrun.domain.Usuario;
import com.gabrielsmm.gestrun.dto.PaginacaoResponse;
import com.gabrielsmm.gestrun.dto.UsuarioResponse;
import com.gabrielsmm.gestrun.dto.UsuarioUpdateRequest;
import com.gabrielsmm.gestrun.mapper.UsuarioMapper;
import com.gabrielsmm.gestrun.security.UsuarioDetails;
import com.gabrielsmm.gestrun.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "2. Usuários", description = "Gerenciamento de usuários")
@SecurityRequirement(name = "Bearer Authentication")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @GetMapping
    @Operation(summary = "Listar todos os usuários de forma paginada", description = "Retorna os usuário cadastrados de forma paginada")
    @PreAuthorize("hasRole('ADMIN')")
    public PaginacaoResponse<UsuarioResponse> listarPaginado(
            @RequestParam(value="pagina", defaultValue="0") Integer pagina,
            @RequestParam(value="registrosPorPagina", defaultValue="10") Integer registrosPorPagina,
            @RequestParam(value="ordem", defaultValue="id") String ordem,
            @RequestParam(value="direcao", defaultValue="ASC") String direcao,
            @RequestParam(value="filtro", required = false) String filtro
    ) {
        Page<Usuario> usuarios = usuarioService.listarPaginado(pagina, registrosPorPagina, ordem, direcao, filtro);
        return usuarioMapper.toPaginacaoResponse(usuarios);
    }

    @GetMapping("/perfil")
    @Operation(summary = "Perfil do usuário autenticado", description = "Retorna os dados do usuário atualmente autenticado")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UsuarioResponse> perfil(@AuthenticationPrincipal UsuarioDetails usuarioDetails) {
        return ResponseEntity.ok(usuarioMapper.toResponse(usuarioDetails.getUsuario()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário específico")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public ResponseEntity<UsuarioResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioMapper.toResponse(usuarioService.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id,
                                                     @Valid @RequestBody UsuarioUpdateRequest request) {
        Usuario atualizado = usuarioService.atualizar(id, request);
        return ResponseEntity.ok(usuarioMapper.toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover usuário", description = "Exclui um usuário pelo ID")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
