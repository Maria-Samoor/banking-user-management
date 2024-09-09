package com.exalt.training.users.exception;

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
    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
    /**
     * Handles EmailAlreadyUsedException by returning a BAD_REQUEST (400) response with the exception message.
     *
     * @param ex the EmailAlreadyUsedException that was thrown.
     * @return a ResponseEntity containing the exception message and a BAD_REQUEST status.
     */
    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<?> handleEmailAlreadyUsedException(EmailAlreadyUsedException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles UserNotFoundException by returning a NOT_FOUND (404) response with the exception message.
     *
     * @param ex the UserNotFoundException that was thrown.
     * @return a ResponseEntity containing the exception message and a NOT_FOUND status.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles UserBlockedException by returning a FORBIDDEN (403) response with the exception message.
     *
     * @param ex the UserBlockedException that was thrown.
     * @return a ResponseEntity containing the exception message and a FORBIDDEN status.
     */
    @ExceptionHandler(UserBlockedException.class)
    public ResponseEntity<?> handleUserBlockedException(UserBlockedException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    /**
     * Handles InvalidCredentialsException by returning an UNAUTHORIZED (401) response with the exception message.
     *
     * @param ex the InvalidCredentialsException that was thrown.
     * @return a ResponseEntity containing the exception message and an UNAUTHORIZED status.
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles UnauthorizedException by returning an UNAUTHORIZED (401) response with the exception message.
     *
     * @param ex the UnauthorizedException that was thrown.
     * @return a ResponseEntity containing the exception message and an UNAUTHORIZED status.
     */
    @ExceptionHandler(UnauthorizedException .class)
    public ResponseEntity<?> handleUnauthorizedException (UnauthorizedException  ex) {
        return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
