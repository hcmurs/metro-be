/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.repository;

import java.util.List;
import org.alfred.ticketservice.model.FarePricing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarePricingRepository extends JpaRepository<FarePricing, Long> {
  List<FarePricing> findByIsActiveTrueOrderByMinDistanceKmAsc();

  boolean existsByMinDistanceKmLessThanEqualAndMaxDistanceKmGreaterThanEqual(
      int maxDistance, int minDistance);

  FarePricing findByIdAndIsActiveTrue(Long id);

  FarePricing findByMinDistanceKmAndMaxDistanceKm(int minDistanceKm, int maxDistanceKm);
}
