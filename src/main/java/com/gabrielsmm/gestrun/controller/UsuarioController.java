package com.gabrielsmm.gestrun.controller;

import com.gabrielsmm.gestrun.domain.Usuario;
import com.gabrielsmm.gestrun.dto.UsuarioRequest;
import com.gabrielsmm.gestrun.dto.UsuarioResponse;
import com.gabrielsmm.gestrun.mapper.UsuarioMapper;
import com.gabrielsmm.gestrun.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Gerenciamento de usuários organizadores")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna todos os usuários cadastrados")
    public List<UsuarioResponse> listar() {
        return usuarioService.listar().stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário específico")
    public ResponseEntity<UsuarioResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioMapper.toResponse(usuarioService.buscarPorId(id)));
    }

//    @PostMapping
//    @Operation(summary = "Criar usuário", description = "Cria um novo usuário organizador")
//    public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody UsuarioRequest request) {
//        Usuario entity = usuarioMapper.toEntity(request);
//        Usuario salvo = usuarioService.criar(entity);
//        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toResponse(salvo));
//    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id,
                                                     @Valid @RequestBody UsuarioRequest request) {
        Usuario usuario = usuarioService.buscarPorId(id);
        usuarioMapper.updateEntityFromDto(request, usuario);
        Usuario atualizado = usuarioService.atualizar(id, usuario);
        return ResponseEntity.ok(usuarioMapper.toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover usuário", description = "Exclui um usuário pelo ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
