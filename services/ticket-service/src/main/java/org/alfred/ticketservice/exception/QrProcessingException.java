package org.alfred.ticketservice.exception;

public class QrProcessingException extends RuntimeException {
    public QrProcessingException(String message) {
        super(message);
    }

    public QrProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
