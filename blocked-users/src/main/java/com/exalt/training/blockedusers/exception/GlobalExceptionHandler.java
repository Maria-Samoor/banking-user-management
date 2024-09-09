package com.exalt.training.blockedusers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling custom exceptions in the application.
 * This class provides centralized exception handling across all controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Builds a standardized error response.
     *
     * @param message The error message.
     * @param status The HTTP status code.
     * @return ResponseEntity containing the error details.
     */
    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    /**
     * Handles UserBlockedException thrown in the application.
     *
     * @param ex The UserBlockedException instance.
     * @return ResponseEntity containing error details.
     */
    @ExceptionHandler(UserBlockedException.class)
    public ResponseEntity<?> handleUserBlockedException(UserBlockedException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
