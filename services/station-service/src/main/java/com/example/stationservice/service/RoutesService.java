package com.example.stationservice.service;

import com.example.stationservice.dto.RoutesRequest;
import com.example.stationservice.model.Routes;

import java.util.List;
import java.util.Optional;

public interface RoutesService {
    Routes createRoute(RoutesRequest request);
    List<Routes> getAllRoutes();
    Optional<Routes> getRouteById(Long id);
    List<Routes> getRoutesByName(String name);
    Routes updateRoute(Long id, Routes route);
    void deleteRoute(Long id);
    boolean existsById(Long id);

    // Additional methods
    Optional<Routes> getRouteByCode(String routeCode);
}
