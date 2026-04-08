package com.micromarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Manejador global de excepciones.
 * Intercepta los errores que lanzan los servicios y controladores
 * y los convierte en respuestas HTTP con el código de estado correcto.
 * Sin esto, Spring devolvería un JSON feo con stack trace al cliente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura errores de validación de @Valid (campos vacíos, formatos incorrectos, etc.).
     * Junta todos los errores de campo en un solo mensaje separado por comas.
     * Devuelve 400 Bad Request.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Captura cuando se intenta registrar un NIT duplicado u otra regla de negocio violada.
     * Devuelve 409 Conflict porque el recurso ya existe.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    /**
     * Captura errores de runtime genéricos que no encajan en los casos anteriores.
     * Devuelve 400 con el mensaje del error.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Red de seguridad final: captura cualquier excepción que no fue manejada arriba.
     * Devuelve 500 con un mensaje genérico — no exponemos detalles internos al cliente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }
}
