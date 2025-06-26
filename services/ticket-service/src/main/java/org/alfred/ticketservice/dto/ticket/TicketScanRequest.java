package org.alfred.ticketservice.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TicketScanRequest(
        @Positive @NotNull(message = "Station ID not null") Long stationId,
        @NotBlank String qrCodeJsonData
) {
}
