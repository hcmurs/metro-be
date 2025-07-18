package org.alfred.ticketservice.service;

import lombok.RequiredArgsConstructor;
import org.alfred.ticketservice.dto.fare_matrix.FarePricingRequest;
import org.alfred.ticketservice.dto.fare_matrix.FarePricingResponse;
import org.alfred.ticketservice.model.FarePricing;
import org.alfred.ticketservice.repository.FarePricingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class FarePricingServiceImpl implements FarePricingService {
    private final FarePricingRepository farePricingRepository;

    @Override
    public FarePricingResponse getFarePricingById(Long id) {
        return farePricingRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Fare pricing not found with id: " + id));
    }
    @Override
    public FarePricingResponse createFarePricing(FarePricingRequest farePricing) {
        FarePricing existingFarePricing = farePricingRepository.findByMinDistanceKmAndMaxDistanceKm(
                farePricing.minDistanceKm(), farePricing.maxDistanceKm());
        boolean overlap = farePricingRepository.existsByMinDistanceKmLessThanEqualAndMaxDistanceKmGreaterThanEqual(
                farePricing.minDistanceKm(), farePricing.maxDistanceKm());
        if(overlap){
            throw new IllegalArgumentException("Khoảng [" +  farePricing.minDistanceKm() + " - " + farePricing.maxDistanceKm() + "] bị trùng hoặc giao với khoảng đã có.");
        }
        if (existingFarePricing != null) {
            throw new IllegalArgumentException("Fare pricing already exists for this distance range");
        }
        FarePricing farePricingEntity = new FarePricing();
        farePricingEntity.setMinDistanceKm(farePricing.minDistanceKm());
        farePricingEntity.setMaxDistanceKm(farePricing.maxDistanceKm());
        farePricingEntity.setPrice(farePricing.price());
        farePricingEntity.setActive(farePricing.isActive());
        farePricingRepository.save(farePricingEntity);
        return mapToResponse(farePricingEntity);
    }

    @Override
    public FarePricingResponse updateFarePricing(FarePricingRequest farePricing, Long id) {
        FarePricing farePricingEntity = farePricingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fare pricing not found with id: " + id));
        farePricingEntity.setMinDistanceKm(farePricing.minDistanceKm());
        farePricingEntity.setMaxDistanceKm(farePricing.maxDistanceKm());
        farePricingEntity.setPrice(farePricing.price());
        farePricingEntity.setActive(farePricing.isActive());
        farePricingRepository.save(farePricingEntity);
        return mapToResponse(farePricingEntity);
    }

    @Override
    public void disableFarePricing(Long id) {
        FarePricing farePricingEntity = farePricingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fare pricing not found with id: " + id));
        farePricingEntity.setActive(false);
        farePricingRepository.save(farePricingEntity);
    }

    @Override
    public List<FarePricingResponse> getAllFarePricing() {
        return farePricingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private FarePricingResponse mapToResponse(FarePricing farePricing) {
        return new FarePricingResponse(
                farePricing.getId(),
                farePricing.getMinDistanceKm(),
                farePricing.getMaxDistanceKm(),
                farePricing.getPrice(),
                farePricing.isActive()
        );
    }
}
