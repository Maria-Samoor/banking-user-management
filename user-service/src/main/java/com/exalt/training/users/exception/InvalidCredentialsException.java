package com.exalt.training.users.exception;
/**
 * Exception thrown when a user provides invalid credentials during authentication.
 */
public class InvalidCredentialsException extends RuntimeException {
    /**
     * Constructs a new InvalidCredentialsException with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
