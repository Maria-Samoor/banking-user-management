package com.exalt.training.blockedusers.exception;

/**
 * Custom exception class for handling user-blocking scenarios.
 */
public class UserBlockedException extends RuntimeException {

    /**
     * Constructs a new UserBlockedException with the specified message.
     *
     * @param message The exception message.
     */
    public UserBlockedException(String message) {
        super(message);
    }
}
