/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.dto.ticket_usage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.alfred.ticketservice.model.enums.UsageTypes;

public record TicketUsageLogRequest(
    @NotBlank String qrCodeData,
    @Positive @NotNull Long stationId,
    @NotNull UsageTypes usageType) {}
