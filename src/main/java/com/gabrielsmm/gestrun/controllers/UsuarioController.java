package com.gabrielsmm.gestrun.controllers;

import com.gabrielsmm.gestrun.dtos.UsuarioRequestDTO;
import com.gabrielsmm.gestrun.dtos.UsuarioResponseDTO;
import com.gabrielsmm.gestrun.entities.Usuario;
import com.gabrielsmm.gestrun.mappers.UsuarioMapper;
import com.gabrielsmm.gestrun.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO request) {
        Usuario entity = usuarioMapper.toEntity(request);
        Usuario salvo = usuarioService.criar(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toResponse(salvo));
    }

    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return usuarioService.listar().stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioMapper.toResponse(usuarioService.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id,
                                                        @Valid @RequestBody UsuarioRequestDTO request) {
        Usuario usuario = usuarioService.buscarPorId(id);
        usuarioMapper.updateEntityFromDto(request, usuario);
        Usuario atualizado = usuarioService.atualizar(id, usuario);
        return ResponseEntity.ok(usuarioMapper.toResponse(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
