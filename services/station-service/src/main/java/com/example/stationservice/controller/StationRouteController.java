package com.example.stationservice.controller;

import com.example.stationservice.config.ApiResponse;
import com.example.stationservice.dto.StationRouteRequest;
import com.example.stationservice.dto.StationRouteResponse;
import com.example.stationservice.model.Stations;
import com.example.stationservice.service.StationRouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/station-routes")
@RequiredArgsConstructor
@Tag(name = "Station Route", description = "Station Route management APIs")
public class StationRouteController {

    private final StationRouteService stationRouteService;

    @GetMapping("/route/{routeId}")
    @Operation(summary = "Get station routes by route ID", description = "Retrieves all station routes for a specific route")
    public ApiResponse<List<StationRouteResponse>> getStationRoutesByRouteId(@PathVariable Long routeId) {
        List<StationRouteResponse> stationRoutes = stationRouteService.getStationRoutesByRouteId(routeId);
        return ApiResponse.success(stationRoutes, "Station routes retrieved successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get station route by ID", description = "Retrieves a specific station route by its ID")
    public ApiResponse<StationRouteResponse> getStationRouteById(@PathVariable Long id) {
        StationRouteResponse stationRoute = stationRouteService.getStationRouteById(id);
        return ApiResponse.success(stationRoute, "Station route retrieved successfully");
    }

    @PostMapping
    @Operation(summary = "Create new station route", description = "Creates a new station route")
    public ApiResponse<StationRouteResponse> saveStationRoute(@Valid @RequestBody StationRouteRequest stationRoute) {
        StationRouteResponse response = stationRouteService.saveStationRoute(stationRoute);
        return ApiResponse.success(response, "Station route created successfully");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update station route", description = "Updates an existing station route")
    public ApiResponse<StationRouteResponse> updateStationRoute(@PathVariable Long id,
                                                                @Valid @RequestBody StationRouteRequest stationRoute) {
        StationRouteResponse response = stationRouteService.updateStationRoute(id, stationRoute);
        return ApiResponse.success(response, "Station route updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete station route", description = "Deletes a station route and reorders remaining routes")
    public ApiResponse<Void> deleteStationRoute(@PathVariable Long id) {
        stationRouteService.deleteStationRoute(id);
        return ApiResponse.success(null, "Station route deleted successfully");
    }

    @PutMapping("/reorder/route/{routeId}")
    @Operation(summary = "Reorder station routes", description = "Reorders station routes after deletion")
    public ApiResponse<Void> reorderStationRouteAfterDelete(@PathVariable Long routeId) {
        stationRouteService.reorderStationRouteAfterDelete(routeId);
        return ApiResponse.success(null, "Station routes reordered successfully");
    }

    @PutMapping("/swap")
    @Operation(summary = "Swap station route order", description = "Swaps the order of two station routes")
    public ApiResponse<Void> swapStationRouteOrder(@RequestParam Long firstStationRouteId,
                                                   @RequestParam Long secondStationRouteId) {
        stationRouteService.swapStationRouteOrder(firstStationRouteId, secondStationRouteId);
        return ApiResponse.success(null, "Station routes swapped successfully");
    }

    @GetMapping("/check-station-on-line")
    @Operation(summary = "Check if station is on line", description = "Checks if a station is between start and end stations on the route")
    public ApiResponse<Boolean> checkStationOnLine(@RequestParam Long startStationId,
                                                   @RequestParam Long endStationId,
                                                   @RequestParam Long thisStationId) {
        boolean isOnLine = stationRouteService.checkStationOnLine(startStationId, endStationId, thisStationId);
        return ApiResponse.success(isOnLine, "Station line check completed");
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update station route status", description = "Updates the status of a station route")
    public ApiResponse<Void> updateStationRouteStatus(@PathVariable Long id,
                                                      @RequestParam Stations.Status status) {
        stationRouteService.updateStationRouteStatus(id, status);
        return ApiResponse.success(null, "Station route status updated successfully");
    }
}