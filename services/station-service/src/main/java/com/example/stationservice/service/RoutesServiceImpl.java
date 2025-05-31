package com.example.stationservice.service;

import com.example.stationservice.dto.RoutesRequest;
import com.example.stationservice.model.Routes;
import com.example.stationservice.repository.RoutesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class RoutesServiceImpl implements RoutesService {
    @Autowired
    private RoutesRepository routesRepository;
    @Override
    public Routes createRoute(RoutesRequest request) {
        if (routesRepository.existsByRouteCode(request.getRouteCode())) {
            throw new RuntimeException("Route code already exists: " + request.getRouteCode());
        }
        Routes route = new Routes();
        route.setRouteCode(request.getRouteCode());
        route.setRouteName(request.getRouteName());
        route.setDistanceInKm(request.getDistanceInKm());
        route.setCreatedAt(LocalDateTime.now());
        route.setUpdatedAt(LocalDateTime.now());
        return routesRepository.save(route);
    }

    @Override
    public List<Routes> getAllRoutes() {
        return routesRepository.findAll();
    }

    @Override
    public Optional<Routes> getRouteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Route ID cannot be null");
        }
        return routesRepository.findById(id);
    }

    @Override
    public List<Routes> getRoutesByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Route name cannot be null or empty");
        }
        return routesRepository.findByRouteNameContainingIgnoreCase(name.trim());
    }

    @Override
    public Routes updateRoute(Long id, Routes routeUpdate) {
            if (id == null) {
                throw new IllegalArgumentException("Route ID cannot be null");
            }

            Routes existingRoute = routesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Route not found with id: " + id));

            // Check if new route code already exists (excluding current route)
            if (routeUpdate.getRouteCode() != null &&
                    !existingRoute.getRouteCode().equals(routeUpdate.getRouteCode()) &&
                    routesRepository.existsByRouteCode(routeUpdate.getRouteCode())) {
                throw new RuntimeException("Route code already exists: " + routeUpdate.getRouteCode());
            }

            // Update fields if they are not null
            if (routeUpdate.getRouteCode() != null && !routeUpdate.getRouteCode().trim().isEmpty()) {
                existingRoute.setRouteCode(routeUpdate.getRouteCode().trim());
            }

            if (routeUpdate.getRouteName() != null && !routeUpdate.getRouteName().trim().isEmpty()) {
                existingRoute.setRouteName(routeUpdate.getRouteName().trim());
            }

            if (routeUpdate.getDistanceInKm() > 0) {
                existingRoute.setDistanceInKm(routeUpdate.getDistanceInKm());
            }

            existingRoute.setUpdatedAt(LocalDateTime.now());

            return routesRepository.save(existingRoute);
        }


    @Override
    public void deleteRoute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Route ID cannot be null");
        }

        if (!routesRepository.existsById(id)) {
            throw new RuntimeException("Route not found with id: " + id);
        }

        // Check if route has stations before deleting
        Optional<Routes> route = routesRepository.findById(id);
        if (route.isPresent() && route.get().getStations() != null && !route.get().getStations().isEmpty()) {
            throw new RuntimeException("Cannot delete route with existing stations. Delete stations first.");
        }

        routesRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        return routesRepository.existsById(id);
    }

    @Override
    public Optional<Routes> getRouteByCode(String routeCode) {
        if (routeCode == null || routeCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Route code cannot be null or empty");
        }
        return routesRepository.findByRouteCode(routeCode.trim());
        }
}
