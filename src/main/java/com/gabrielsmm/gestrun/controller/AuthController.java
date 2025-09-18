package com.gabrielsmm.gestrun.controller;

import com.gabrielsmm.gestrun.dto.LoginRequest;
import com.gabrielsmm.gestrun.dto.LoginResponse;
import com.gabrielsmm.gestrun.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Gerenciamento de autenticação e autorização")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica um usuário e retorna um token JWT")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.senha())
        );

        String token = jwtUtil.gerarToken(authentication.getName());

        Instant expiraEm = jwtUtil.getExpirationDateFromNow().toInstant();
        String expiraEmIso = expiraEm.toString(); // Formato ISO 8601 (UTC)

        LoginResponse response = new LoginResponse(token, "Bearer", expiraEmIso);
        return ResponseEntity.ok(response);
    }

}
