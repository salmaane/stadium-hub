package org.salmane.matchservice.exception;

public class TicketsCreationFailureException extends RuntimeException {
    public TicketsCreationFailureException(String message) {
        super(message);
    }

    public TicketsCreationFailureException() {
        super();
    }
}
