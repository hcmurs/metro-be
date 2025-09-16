/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.dto.ticket_type;

import jakarta.validation.constraints.*;

public record UpdateTicketTypeRequest(
    @NotNull(message = "Ticket type ID is required for updates") Long ticketTypeId,
    @NotBlank(message = "Name cannot be empty")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,
    @Size(max = 500, message = "Description cannot exceed 500 characters") String description,
    @NotNull(message = "Price is required")
        @PositiveOrZero(message = "Price must be zero or positive")
        @DecimalMax(value = "10000.0", message = "Price cannot exceed 10,000")
        float price,
    boolean isActive,
    @NotNull(message = "Validity duration is required")
        @Positive(message = "Validity duration must be greater than zero")
        @Max(value = 365, message = "Validity duration cannot exceed 365")
        int validityDuration) {}
