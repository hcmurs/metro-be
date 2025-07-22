package org.alfred.ticketservice.controller;

import lombok.RequiredArgsConstructor;
import org.alfred.ticketservice.config.ApiResponse;
import org.alfred.ticketservice.dto.fare_matrix.FarePricingRequest;
import org.alfred.ticketservice.dto.fare_matrix.FarePricingResponse;
import org.alfred.ticketservice.service.FarePricingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ts/fare-pricing")
@RequiredArgsConstructor
@Tag(name = "Fare Pricing", description = "Fare Pricing management APIs")
public class FarePricingController {

    private final FarePricingService farePricingService;

    @GetMapping("/{id}")
    @Operation(summary = "Get fare pricing by ID", description = "Retrieves fare pricing details by ID")
    public ApiResponse<FarePricingResponse> getFarePricingById(@PathVariable Long id) {
        FarePricingResponse farePricing = farePricingService.getFarePricingById(id);
        return ApiResponse.success(farePricing, "Fare pricing retrieved successfully");
    }

    @GetMapping
    @Operation(summary = "Get all fare pricing", description = "Retrieves all fare pricing entries")
    public ApiResponse<List<FarePricingResponse>> getAllFarePricing() {
        List<FarePricingResponse> farePricingList = farePricingService.getAllFarePricing();
        return ApiResponse.success(farePricingList, "All fare pricing retrieved successfully");
    }

    @PostMapping
    @Operation(summary = "Create new fare pricing", description = "Creates a new fare pricing entry")
    public ApiResponse<FarePricingResponse> createFarePricing(@Valid @RequestBody FarePricingRequest farePricing) {
        FarePricingResponse response = farePricingService.createFarePricing(farePricing);
        return ApiResponse.success(response, "Fare pricing created successfully");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update fare pricing", description = "Updates an existing fare pricing entry")
    public ApiResponse<FarePricingResponse> updateFarePricing(@PathVariable Long id,
                                                             @Valid @RequestBody FarePricingRequest farePricing) {
        FarePricingResponse response = farePricingService.updateFarePricing(farePricing, id);
        return ApiResponse.success(response, "Fare pricing updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Disable fare pricing", description = "Disables an existing fare pricing entry")
    public ApiResponse<Void> disableFarePricing(@PathVariable Long id) {
        farePricingService.disableFarePricing(id);
        return ApiResponse.success(null, "Fare pricing disabled successfully");
    }
}