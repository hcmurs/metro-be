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
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record FarePricingRequest(
    @PositiveOrZero(message = "Min distance must be a positive number")
        @NotNull(message = "Min distance must be a positive number")
        int minDistanceKm,
    @Positive(message = "Max distance must be a positive number")
        @NotNull(message = "Max distance must be a positive number")
        int maxDistanceKm,
    @Positive(message = "price must be a positive number")
        @NotNull(message = "price must be a valid number")
        float price,
    @NotNull(message = "isActive must be true or false") boolean isActive) {}
