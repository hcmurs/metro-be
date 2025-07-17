package org.alfred.ticketservice.dto.ticket_usage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.alfred.ticketservice.model.enums.UsageTypes;

public record TicketUsageLogRequest(
        @NotBlank String qrCodeData,
        @Positive @NotNull Long stationId,
        @NotNull UsageTypes usageType
        ) {
}
