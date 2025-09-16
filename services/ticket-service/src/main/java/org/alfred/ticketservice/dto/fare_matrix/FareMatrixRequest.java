/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.dto.fare_matrix;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record FareMatrixRequest(
    @NotNull(message = "Start station ID is required")
        @PositiveOrZero(message = "Start station ID must be valid")
        Long startStationId,
    @NotNull(message = "End station ID is required")
        @PositiveOrZero(message = "End station ID must be valid")
        Long endStationId,
    @NotNull(message = "Is active status is required") boolean isActive,
    @NotBlank(message = "Name cannot be empty")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name) {}
