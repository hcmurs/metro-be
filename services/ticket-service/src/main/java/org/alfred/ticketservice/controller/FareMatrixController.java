package org.alfred.ticketservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alfred.ticketservice.config.ApiResponse;
import org.alfred.ticketservice.dto.fare_matrix.*;
import org.alfred.ticketservice.service.FareMatrixService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ts/fare-matrices")
@RequiredArgsConstructor
@Slf4j
@Validated
public class FareMatrixController {

    private final FareMatrixService fareMatrixService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FareMatrixResponse>> getFareMatrixById(@PathVariable Long id) {
        log.info("Request to get fare matrix by id: {}", id);
        FareMatrixResponse fareMatrix = fareMatrixService.getFareMatrixById(id);
        return ResponseEntity.ok(ApiResponse.success(fareMatrix, "Fare matrix retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FareMatrixResponse>>> getAllFareMatrices() {
        log.info("Request to get all fare matrices");
        List<FareMatrixResponse> fareMatrices = fareMatrixService.getAllFareMatrices();
        return ResponseEntity.ok(ApiResponse.success(fareMatrices, "Fare matrices retrieved successfully"));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_STAFF')")
    public ResponseEntity<ApiResponse<FareMatrixResponse>> createFareMatrix(@Valid @RequestBody FareMatrixRequest request) {
        log.info("Request to create fare matrix between stations: {} and {}",
                request.startStationId(), request.endStationId());
        FareMatrixResponse createdFareMatrix = fareMatrixService.createFareMatrix(request);
        return new ResponseEntity<>(
                ApiResponse.success(createdFareMatrix, "Fare matrix created successfully"),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update/{fareMatrixId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_STAFF')")
    public ResponseEntity<ApiResponse<FareMatrixResponse>> updateFareMatrix(@Valid @RequestBody FareMatrixRequest request,
                                                                           @PathVariable Long fareMatrixId)  {
        FareMatrixResponse updatedFareMatrix = fareMatrixService.updateFareMatrix(request,fareMatrixId);
        return ResponseEntity.ok(ApiResponse.success(updatedFareMatrix, "Fare matrix updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_STAFF')")
    public ResponseEntity<ApiResponse<Void>> deleteFareMatrix(@PathVariable Long id) {
        log.info("Request to delete (deactivate) fare matrix with ID: {}", id);
        fareMatrixService.deleteFareMatrix(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Fare matrix deactivated successfully"));
    }

    @GetMapping("/by-station/{stationId}")
    public ResponseEntity<ApiResponse<List<FareMatrixResponse>>> getFareMatricesByStartStation(
            @Valid @PathVariable Long stationId) {
        log.info("Request to get fare matrices by start station ID: {}", stationId);
        List<FareMatrixResponse> fareMatrices = fareMatrixService.getFareMatricesByStartStationId(stationId);
        return ResponseEntity.ok(ApiResponse.success(fareMatrices, "Fare matrices retrieved successfully"));
    }

    @GetMapping("/{fareMatrixId}/validate-station/{stationId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER') or hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_STAFF')")
    public ResponseEntity<ApiResponse<Boolean>> validateStationInFareMatrix(
            @PathVariable Long fareMatrixId,
            @PathVariable Long stationId) {
        log.info("Request to validate if station ID: {} is in fare matrix ID: {}", stationId, fareMatrixId);
        boolean isValid = fareMatrixService.isStationInFareMatrix(stationId, fareMatrixId);
        return ResponseEntity.ok(ApiResponse.success(isValid,
                isValid ? "Station is valid for this fare matrix" : "Station is not valid for this fare matrix"));
    }

    @PostMapping("/get-fare")
    public ResponseEntity<ApiResponse<FareMatrixResponse>> getFare(
            @RequestBody @Valid FindFareRequest fareRequest) {
        FareMatrixResponse fare = fareMatrixService.getFareMatrixByStations(fareRequest);
        return ResponseEntity.ok(ApiResponse.success(fare, "Fare retrieved successfully"));
    }

    @PutMapping("/update-status/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_STAFF')")
    public ResponseEntity<ApiResponse<FareMatrixResponse>> updateStatusFare(
            @PathVariable Long id, @RequestParam boolean status) {
        log.info("Request to update status of fare matrix with ID: {}", id);
        FareMatrixResponse updatedFareMatrix = fareMatrixService.updateStatusFare(id, status);
        return ResponseEntity.ok(ApiResponse.success(updatedFareMatrix, "Fare matrix status updated successfully"));
    }
}