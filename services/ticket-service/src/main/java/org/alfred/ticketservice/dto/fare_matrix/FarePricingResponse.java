package org.alfred.ticketservice.dto.fare_matrix;

public record FarePricingResponse(
    Long id,
    int minDistanceKm,
    int maxDistanceKm,
    float fare,
    boolean isActive
) {
}
