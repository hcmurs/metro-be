package com.example.stationservice.controller;

import com.example.stationservice.config.ApiResponse;
import com.example.stationservice.dto.RoutesRequest;
import com.example.stationservice.model.Routes;
import com.example.stationservice.service.RoutesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = "*")
@Tag(name = "Routes", description = "Operations related to routes")
public class RoutesController {

    @Autowired
    private RoutesService routesService;

    @PostMapping
    public ApiResponse<Routes> createRoute(@RequestBody RoutesRequest request) {
        Routes route = routesService.createRoute(request);
        return ApiResponse.success(route, "Route created successfully");
    }

    @GetMapping
    public ApiResponse<List<Routes>> getAllRoutes() {
        List<Routes> routes = routesService.getAllRoutes();
        return ApiResponse.success(routes, "Routes retrieved successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<Routes> getRouteById(@PathVariable Long id) {
        Optional<Routes> route = routesService.getRouteById(id);
        if (route.isPresent()) {
            return ApiResponse.success(route.get(), "Route found");
        } else {
            throw new IllegalArgumentException("Route not found with id: " + id);
        }
    }

    @GetMapping("/search")
    public ApiResponse<List<Routes>> getRoutesByName(@RequestParam String name) {
        List<Routes> routes = routesService.getRoutesByName(name);
        return ApiResponse.success(routes, "Routes found by name");
    }


    @GetMapping("/code/{routeCode}")
    public ApiResponse<Routes> getRouteByCode(@PathVariable String routeCode) {
        Optional<Routes> route = routesService.getRouteByCode(routeCode);
        if (route.isPresent()) {
            return ApiResponse.success(route.get(), "Route found by code");
        } else {
            throw new IllegalArgumentException("Route not found with code: " + routeCode);
        }
    }


    @PutMapping("/{id}")
    public ApiResponse<Routes> updateRoute(@PathVariable Long id, @RequestBody Routes route) {
        Routes updatedRoute = routesService.updateRoute(id, route);
        return ApiResponse.success(updatedRoute, "Route updated successfully");
    }


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