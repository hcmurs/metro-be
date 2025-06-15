package com.example.stationservice.service;

import com.example.stationservice.dto.RoutesRequest;
import com.example.stationservice.dto.RoutesResponse;
import com.example.stationservice.model.Routes;

import java.util.List;
import java.util.Optional;

public interface RoutesService {
    RoutesResponse createRoute(RoutesRequest request);
    List<RoutesResponse> getAllRoutes();
    Optional<RoutesResponse> getRouteById(Long id);
    List<RoutesResponse> getRoutesByName(String name);
    RoutesResponse updateRoute(Long id, RoutesRequest route);
    void deleteRoute(Long id);
    boolean existsById(Long id);

    // Additional methods
    Optional<RoutesResponse> getRouteByCode(String routeCode);
}
