package com.exalt.training.users.exception;
/**
 * Exception thrown when an email is already in use in the system.
 * This custom exception is used to indicate that the provided email is not unique.
 */
public class EmailAlreadyUsedException extends RuntimeException{
    /**
     * Constructs a new EmailAlreadyUsedException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception.
     */
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
