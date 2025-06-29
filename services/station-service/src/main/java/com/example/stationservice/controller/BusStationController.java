package com.example.stationservice.controller;

import com.example.stationservice.config.ApiResponse;
import com.example.stationservice.dto.Response;
import com.example.stationservice.dto.RoutesResponse;
import com.example.stationservice.model.BusRoutes;
import com.example.stationservice.model.BusStationRoutes;
import com.example.stationservice.model.BusStations;
import com.example.stationservice.service.BusStationsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bus")
@RequiredArgsConstructor
@Tag(name = "Bus", description = "Operations related to routes")
public class BusStationController {
    private final BusStationsService busStationService;

    @GetMapping()
    public ApiResponse<List<BusStations>> getBusStations() {
        List<BusStations> busStations = busStationService.getBusStations();
        return ApiResponse.success(busStations, "Bus stations found");
    }

    @GetMapping("/{id}")
    public ApiResponse<BusStations> getBusStationById(@PathVariable String id) {
        BusStations stations = busStationService.getBusStationById(id);
        return ApiResponse.success(stations, "Bus Station found");
    }

    @PostMapping()
    public ApiResponse<BusStations> createBusStation(@RequestBody BusStations busStation) {
        BusStations createdBusStation = busStationService.createBusStation(busStation);
        return ApiResponse.success(createdBusStation, "Bus Station created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<BusStations> updateBusStation(@PathVariable String id, @RequestBody BusStations busStation) {
        BusStations updatedBusStation = busStationService.updateBusStation(id, busStation);
        return ApiResponse.success(updatedBusStation, "Bus Station updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBusStation(@PathVariable String id) {
        busStationService.deleteBusStation(id);
        return ApiResponse.success(null, "Bus Station deleted successfully");
    }

    @GetMapping("/routes/{stationId}")
    @PermitAll
    public ApiResponse<Response.BusStationResponse> getBusRoutes(@PathVariable String stationId) {
        Response.BusStationResponse busStationResponse = busStationService.getBusStationRoutesStationId(stationId);
        return ApiResponse.success(busStationResponse, "Bus routes found");
    }
    @GetMapping("/routes")
    @PermitAll
    public ApiResponse<List<BusRoutes>> getBusRoutes() {
        List<BusRoutes> busRoutes = busStationService.getBusRoutes();
        return ApiResponse.success(busRoutes, "Bus routes found");
    }
    @GetMapping("/bus-routes")
    @PermitAll
    public ApiResponse<List<BusStationRoutes>> getBusStationRoutes() {
        List<BusStationRoutes> busRoutes = busStationService.getBusStationRoutes();
        return ApiResponse.success(busRoutes, "Bus routes found for station");
    }
}
