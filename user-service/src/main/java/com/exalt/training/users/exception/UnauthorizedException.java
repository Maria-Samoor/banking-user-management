package com.exalt.training.users.exception;

/**
 * Exception thrown when a user is not authorized to access a particular resource.
 */
public class UnauthorizedException extends RuntimeException {
    /**
     * Constructs a new UnauthorizedException with the specified detail message.
     *
     * @param message the detail message.
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
