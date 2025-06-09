package org.alfred.ticketservice.model.enums;

public enum TicketStatus {
    USED("used"),
    EXPIRED("expired"),
    NOT_USED("not_used"),
    PENDING("pending"),
    CANCELLED("cancelled");

    private final String status;

    TicketStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
