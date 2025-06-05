package org.alfred.ticketservice.config;

public class TicketProcessingException extends RuntimeException {
    public TicketProcessingException(String message) {
        super(message);
    }

    public TicketProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
