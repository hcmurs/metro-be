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

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record FindFareRequest(
    @NotNull(message = "Start station ID is required")
        @PositiveOrZero(message = "Start station ID must be valid")
        Long startStationId,
    @NotNull(message = "End station ID is required")
        @PositiveOrZero(message = "End station ID must be valid")
        Long endStationId) {}
