package com.fasec.auditoria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Trata erros de anotações de validação (@NotBlank, @NotNull, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarErrosValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> mensagensCampos = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            mensagensCampos.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> corpoResposta = new HashMap<>();
        corpoResposta.put("timestamp", LocalDateTime.now());
        corpoResposta.put("status", HttpStatus.BAD_REQUEST.value());
        corpoResposta.put("erro", "Erro de Validação de Dados");
        corpoResposta.put("detalhes", mensagensCampos);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpoResposta);
    }

    // Trata JSON com formato quebrado ou tipos incompatíveis (ex: texto num campo de data)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> tratarJsonInvalido(HttpMessageNotReadableException ex) {
        Map<String, Object> corpoResposta = new HashMap<>();
        corpoResposta.put("timestamp", LocalDateTime.now());
        corpoResposta.put("status", HttpStatus.BAD_REQUEST.value());
        corpoResposta.put("erro", "Corpo da Requisição Inválido");
        corpoResposta.put("mensagem", "Formato JSON mal estruturado ou tipos de dados incompatíveis.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpoResposta);
    }

    // Trata qualquer erro inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> tratarErrosGenericos(Exception ex) {
        Map<String, Object> corpoResposta = new HashMap<>();
        corpoResposta.put("timestamp", LocalDateTime.now());
        corpoResposta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        corpoResposta.put("erro", "Erro Interno no Servidor");
        corpoResposta.put("mensagem", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(corpoResposta);
    }
}