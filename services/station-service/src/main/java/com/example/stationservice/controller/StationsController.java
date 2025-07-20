package com.example.stationservice.controller;

import com.example.stationservice.config.ApiResponse;
import com.example.stationservice.dto.StationsRequest;
import com.example.stationservice.dto.StationsResponse;
import com.example.stationservice.model.Stations;
import com.example.stationservice.service.StationsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.nio.file.Files;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stations")
@Slf4j
@Tag(name = "Stations", description = "Operations related to stations")
public class StationsController {

    @Autowired
    private StationsService stationsService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ApiResponse<StationsResponse> createStation(@RequestBody StationsRequest request) {
        StationsResponse station = stationsService.createStation(request);
        return ApiResponse.success(station, "Station created successfully");
    }

    @GetMapping
    public ApiResponse<List<StationsResponse>> getAllStations() {
        List<StationsResponse> stations = stationsService.getAllStations();
        return ApiResponse.success(stations, "Stations retrieved successfully");
    }

    @GetMapping(value = "/static-bus-stations", produces = "application/json")
    public String getStationsJson() {
        try {
            ClassPathResource resource = new ClassPathResource("json/station.json");
            return Files.readString(resource.getFile().toPath());
        } catch (IOException e) {
            log.error("Failed to read file: " + e.getMessage());
        }
        return "[]";
    }

    @GetMapping("/{id}")
    public ApiResponse<StationsResponse> getStationById(@PathVariable Long id) {
        Optional<StationsResponse> station = stationsService.getStationById(id);
        if (station.isPresent()) {
            return ApiResponse.success(station.get(), "Station found");
        } else {
            throw new IllegalArgumentException("Station not found with id: " + id);
        }
    }

    @GetMapping("/search")
    public ApiResponse<List<StationsResponse>> getStationsByName(@RequestParam String name) {
        List<StationsResponse> stations = stationsService.getStationsByName(name);
        return ApiResponse.success(stations, "Stations found by name");
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ApiResponse<StationsResponse> updateStation(@PathVariable Long id, @RequestBody @Valid StationsRequest station) {
        StationsResponse updatedStation = stationsService.updateStation(id, station);
        return ApiResponse.success(updatedStation, "Station updated successfully");
    }

    @PostMapping("/status/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ApiResponse<StationsResponse> updateStationStatus(@PathVariable Long id, @RequestParam Stations.Status status) {
        StationsResponse updatedStation = stationsService.updateStationStatus(id, status);
        return ApiResponse.success(updatedStation, "Station status updated successfully");
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ApiResponse<Void> deleteStation(@PathVariable Long id) {
        stationsService.deleteStation(id);
        return ApiResponse.success("Station deleted successfully");
    }
    @GetMapping("/{id}/exists")
    public ApiResponse<Boolean> checkStationExists(@PathVariable Long id) {
        boolean exists = stationsService.existsById(id);
        return ApiResponse.success(exists, "Station existence checked");
    }


}
