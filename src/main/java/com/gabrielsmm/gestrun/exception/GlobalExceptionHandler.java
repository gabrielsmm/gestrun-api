package com.gabrielsmm.gestrun.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Exceções genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleGenericException(Exception ex, HttpServletRequest request) {
        ErroResposta resposta = buildErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno no servidor",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                request
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }

    // Erros de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ErroValidacao resposta = new ErroValidacao(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validação falhou",
                "Um ou mais campos estão inválidos.",
                request.getRequestURI()
        );

        for (FieldError f : ex.getBindingResult().getFieldErrors()) {
            resposta.adicionarErro(f.getField(), f.getDefaultMessage());
        }

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

    // Autorização negada
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErroResposta> handleAuthorizationDenied(AuthorizationDeniedException ex, HttpServletRequest request) {
        ErroResposta resposta = buildErroResposta(
                HttpStatus.FORBIDDEN,
                "Acesso negado",
                "Você não tem permissão para acessar este recurso",
                request
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resposta);
    }

    // Exceções customizadas
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

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ErroResposta> handleRecursoDuplicado(RecursoDuplicadoException ex, HttpServletRequest request) {
        ErroResposta resposta = buildErroResposta(
                HttpStatus.CONFLICT,
                "Recurso duplicado",
                ex.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
    }

    @ExceptionHandler(AcessoNegadoException.class)
    public ResponseEntity<ErroResposta> handleAcessoNegado(AcessoNegadoException ex, HttpServletRequest request) {
        ErroResposta resposta = buildErroResposta(
                HttpStatus.FORBIDDEN,
                "Acesso negado",
                ex.getMessage(),
                request
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resposta);
    }

    private ErroResposta buildErroResposta(HttpStatus status, String erro, String mensagem, HttpServletRequest request) {
        return new ErroResposta(
                LocalDateTime.now(),
                status.value(),
                erro,
                mensagem,
                request.getRequestURI()
        );
    }

}
