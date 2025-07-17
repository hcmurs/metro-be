package org.alfred.ticketservice.dto.ticket;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TicketRequest{
    public record TicketType(
        @Positive @NotNull(message = "Ticket type ID cannot be null")
        Long id
    ) {}
    public record FareMatrix(
        @Positive @NotNull(message = "Fare matrix ID cannot be null")
        Long id
    ) {}
}
