package com.exalt.training.users.exception;

/**
 * Exception thrown when a user is blocked and cannot perform certain actions.
 */
public class UserBlockedException extends RuntimeException {

    /**
     * Constructs a new UserBlockedException with the specified detail message.
     *
     * @param message the detail message.
     */
    public UserBlockedException(String message) {
        super(message);
    }
}
