package br.com.ifsp.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("satus", HttpStatus.BAD_REQUEST.value());
        response.put("timestamp", Instant.now());
        response.put("message", "Erro de validação dos campos");
        response.put("errors", e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    assert error.getDefaultMessage() != null;
                    return Map.of("field", Objects.requireNonNull(error.getField()), "message", error.getDefaultMessage());
                })
                .toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("satus", HttpStatus.UNAUTHORIZED.value());
        response.put("timestamp", Instant.now());
        response.put("error", "Credenciais inválidas");
        response.put("message", "Credenciais inválidas ou usuário não existe.");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
