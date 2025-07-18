package org.alfred.ticketservice.service;

import org.alfred.ticketservice.dto.fare_matrix.FarePricingRequest;
import org.alfred.ticketservice.dto.fare_matrix.FarePricingResponse;

import java.util.List;

public interface FarePricingService {
    FarePricingResponse getFarePricingById(Long id);
    FarePricingResponse createFarePricing(FarePricingRequest farePricing);
    FarePricingResponse updateFarePricing(FarePricingRequest farePricing, Long id);
    void disableFarePricing(Long id);
    List<FarePricingResponse> getAllFarePricing();
}
