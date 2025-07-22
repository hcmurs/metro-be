package com.example.stationservice.controller;

import com.example.stationservice.config.ApiResponse;
import com.example.stationservice.dto.RoutesRequest;
import com.example.stationservice.dto.RoutesResponse;
import com.example.stationservice.model.Routes;
import com.example.stationservice.service.RoutesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/routes")
@Tag(name = "Routes", description = "Operations related to routes")
public class RoutesController {

    @Autowired
    private RoutesService routesService;

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ApiResponse<RoutesResponse> createRoute(@RequestBody @Valid RoutesRequest request) {
        RoutesResponse route = routesService.createRoute(request);
        return ApiResponse.success(route, "Route created successfully");
    }

    @GetMapping
    public ApiResponse<List<RoutesResponse>> getAllRoutes() {
        List<RoutesResponse> routes = routesService.getAllRoutes();
        return ApiResponse.success(routes, "Routes retrieved successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<RoutesResponse> getRouteById(@PathVariable Long id) {
        Optional<RoutesResponse> route = routesService.getRouteById(id);
        if (route.isPresent()) {
            return ApiResponse.success(route.get(), "Route found");
        } else {
            throw new IllegalArgumentException("Route not found with id: " + id);
        }
    }

    @GetMapping("/search")
    public ApiResponse<List<RoutesResponse>> getRoutesByName(@RequestParam String name) {
        List<RoutesResponse> routes = routesService.getRoutesByName(name);
        return ApiResponse.success(routes, "Routes found by name");
    }


    @GetMapping("/code/{routeCode}")
    public ApiResponse<RoutesResponse> getRouteByCode(@PathVariable String routeCode) {
        Optional<RoutesResponse> route = routesService.getRouteByCode(routeCode);
        if (route.isPresent()) {
            return ApiResponse.success(route.get(), "Route found by code");
        } else {
            throw new IllegalArgumentException("Route not found with code: " + routeCode);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<RoutesResponse> updateRoute(@PathVariable Long id, @RequestBody RoutesRequest route) {
        RoutesResponse updatedRoute = routesService.updateRoute(id, route);
        return ApiResponse.success(updatedRoute, "Route updated successfully");
    }

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRoute(@PathVariable Long id) {
        routesService.deleteRoute(id);
        return ApiResponse.success("Route deleted successfully");
    }

    @GetMapping("/{id}/exists")
    public ApiResponse<Boolean> checkRouteExists(@PathVariable Long id) {
        boolean exists = routesService.existsById(id);
        return ApiResponse.success(exists, "Route existence checked");
    }

}