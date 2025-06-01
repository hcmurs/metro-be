package com.example.stationservice.controller;

import com.example.stationservice.config.ApiResponse;
import com.example.stationservice.dto.StationsRequest;
import com.example.stationservice.dto.StationsResponse;
import com.example.stationservice.model.Stations;
import com.example.stationservice.service.StationsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stations")
@CrossOrigin(origins = "*")
@Tag(name = "Stations", description = "Operations related to stations")
public class StationsController {

    @Autowired
    private StationsService stationsService;

    @PostMapping
    public ApiResponse<StationsResponse> createStation(@RequestBody StationsRequest request) {
        StationsResponse station = stationsService.createStation(request);
        return ApiResponse.success(station, "Station created successfully");
    }

    @GetMapping
    public ApiResponse<List<StationsResponse>> getAllStations() {
        List<StationsResponse> stations = stationsService.getAllStations();
        return ApiResponse.success(stations, "Stations retrieved successfully");
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

    @GetMapping("/route/{routeId}")
    public ApiResponse<List<StationsResponse>> getStationsByRouteId(@PathVariable Long routeId) {
        List<StationsResponse> stations = stationsService.getStationsByRouteId(routeId);
        return ApiResponse.success(stations, "Stations found for route");
    }

    @PutMapping("/{id}")
    public ApiResponse<StationsResponse> updateStation(@PathVariable Long id, @RequestBody Stations station) {
        StationsResponse updatedStation = stationsService.updateStation(id, station);
        return ApiResponse.success(updatedStation, "Station updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStation(@PathVariable Long id) {
        stationsService.deleteStation(id);
        return ApiResponse.success("Station deleted successfully");
    }
    @GetMapping("/{id}/exists")
    public ApiResponse<Boolean> checkStationExists(@PathVariable Long id) {
        boolean exists = stationsService.existsById(id);
        return ApiResponse.success(exists, "Station existence checked");
    }

    @GetMapping("/check-line")
    public ApiResponse<Boolean> checkStationOnLine(@RequestParam Long startStationId,
                                                    @RequestParam Long endStationId,
                                                    @RequestParam Long thisStation) {
        boolean isOnLine = stationsService.checkStationOnLine(startStationId, endStationId, thisStation);
        return ApiResponse.success(isOnLine, "Checked if station is on line");
    }
}
