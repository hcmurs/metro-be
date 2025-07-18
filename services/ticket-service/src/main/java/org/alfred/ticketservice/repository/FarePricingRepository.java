package org.alfred.ticketservice.repository;

import org.alfred.ticketservice.model.FarePricing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarePricingRepository extends JpaRepository<FarePricing, Long> {
    List<FarePricing> findByIsActiveTrueOrderByMinDistanceKmAsc();
    boolean existsByMinDistanceKmLessThanEqualAndMaxDistanceKmGreaterThanEqual(int maxDistance, int minDistance);
    FarePricing findByIdAndIsActiveTrue(Long id);
    FarePricing findByMinDistanceKmAndMaxDistanceKm(int minDistanceKm, int maxDistanceKm);
}
