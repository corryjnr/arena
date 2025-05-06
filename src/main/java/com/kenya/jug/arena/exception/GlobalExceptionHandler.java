package com.kenya.jug.arena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, Object> response = new LinkedHashMap<>();
        Map<String, String> fieldErrors = new LinkedHashMap<>();


        exception.getBindingResult().getFieldErrors().forEach(error -> {
                fieldErrors.put(error.getField(), error.getDefaultMessage());}
        );

        response.put("error", true);
        response.put("message", "Validation failed");
        response.put("errors", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException exception) {
        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("error", true);
        errorResponse.put("message", exception.getReason());

        return ResponseEntity.status(exception.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotSupported(HttpRequestMethodNotSupportedException exception) {
        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("error", true);
        errorResponse.put("message", "Request method '" + exception.getMethod() + "' is not supported for this endpoint.");
        errorResponse.put("supportedMethods", exception.getSupportedHttpMethods());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }
}
