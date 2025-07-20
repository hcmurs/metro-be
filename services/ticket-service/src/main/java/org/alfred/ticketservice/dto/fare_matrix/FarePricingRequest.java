package org.alfred.ticketservice.dto.fare_matrix;

import jakarta.validation.constraints.Min;
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
        @NotNull(message = "isActive must be true or false")
        boolean isActive
) {
}
