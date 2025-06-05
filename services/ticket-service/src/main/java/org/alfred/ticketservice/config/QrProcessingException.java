package org.alfred.ticketservice.config;

public class QrProcessingException extends RuntimeException {
    public QrProcessingException(String message) {
        super(message);
    }

    public QrProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
