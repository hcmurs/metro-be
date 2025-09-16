/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.service;

import java.util.List;
import org.alfred.ticketservice.dto.fare_matrix.FarePricingRequest;
import org.alfred.ticketservice.dto.fare_matrix.FarePricingResponse;

public interface FarePricingService {
  FarePricingResponse getFarePricingById(Long id);

  FarePricingResponse createFarePricing(FarePricingRequest farePricing);

  FarePricingResponse updateFarePricing(FarePricingRequest farePricing, Long id);

  void disableFarePricing(Long id);

  List<FarePricingResponse> getAllFarePricing();
}
