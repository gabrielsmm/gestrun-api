package com.gabrielsmm.gestrun.controller;

import com.gabrielsmm.gestrun.domain.Usuario;
import com.gabrielsmm.gestrun.dto.LoginRequest;
import com.gabrielsmm.gestrun.dto.LoginResponse;
import com.gabrielsmm.gestrun.dto.UsuarioRequest;
import com.gabrielsmm.gestrun.dto.UsuarioResponse;
import com.gabrielsmm.gestrun.mapper.UsuarioMapper;
import com.gabrielsmm.gestrun.security.JwtUtil;
import com.gabrielsmm.gestrun.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Gerenciamento de autenticação e autorização")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica um usuário e retorna um token JWT")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.senha())
        );

        String token = jwtUtil.gerarToken(authentication.getName());
        String expiraEm = jwtUtil.getExpirationDateFromNow().toInstant().toString();

        return ResponseEntity.ok(new LoginResponse(token, "Bearer", expiraEm));
    }

    @PostMapping("/registrar")
    @Operation(summary = "Registrar", description = "Registra um novo usuário")
    public ResponseEntity<UsuarioResponse> registrar(@RequestBody UsuarioRequest usuarioRequest) {
        Usuario novoUsuario = usuarioMapper.toEntity(usuarioRequest);
        Usuario usuarioSalvo = usuarioService.criar(novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toResponse(usuarioSalvo));
    }

}
