package com.gabrielsmm.gestrun.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ErroResposta buildErroResposta(HttpStatus status, String erro, String mensagem, HttpServletRequest request) {
        return new ErroResposta(
                LocalDateTime.now(),
                status.value(),
                erro,
                mensagem,
                request.getRequestURI()
        );
    }

    // Exceções genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleGenericException(Exception ex, HttpServletRequest request) {
        ErroResposta resposta = buildErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno no servidor",
                ex.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }

    // Erros de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String mensagens = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErroResposta resposta = buildErroResposta(
                HttpStatus.BAD_REQUEST,
                "Validação falhou",
                mensagens,
                request
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    // Credenciais inválidas
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroResposta> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        ErroResposta resposta = buildErroResposta(
                HttpStatus.UNAUTHORIZED,
                "Falha de autenticação",
                "E-mail ou senha incorretos",
                request
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resposta);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex, HttpServletRequest request) {
        ErroResposta resposta = buildErroResposta(
                HttpStatus.NOT_FOUND,
                "Recurso não encontrado",
                ex.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

}
