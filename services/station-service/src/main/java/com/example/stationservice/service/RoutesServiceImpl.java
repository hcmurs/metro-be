package com.example.stationservice.service;

import com.example.stationservice.dto.RoutesRequest;
import com.example.stationservice.dto.RoutesResponse;
import com.example.stationservice.model.Routes;
import com.example.stationservice.model.StationRoute;
import com.example.stationservice.repository.RoutesRepository;
import com.example.stationservice.repository.StationRouteRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class RoutesServiceImpl implements RoutesService {
    @Autowired
    private RoutesRepository routesRepository;
    @Autowired
    private StationRouteRepository stationRouteRepository;
    @Autowired
    private StationRouteService stationRouteService;
    @Autowired
    private ModelMapper modelMapper;
    @Transactional
    @Override
    public RoutesResponse createRoute(RoutesRequest request) {
        if (routesRepository.existsByRouteCode(request.getRouteCode())) {
            throw new EntityExistsException("Route code already exists: " + request.getRouteCode());
        }
        Routes route = new Routes();
        route.setRouteCode(request.getRouteCode());
        route.setRouteName(request.getRouteName());
        route.setDistanceInKm(request.getDistanceInKm());
        route.setCreatedAt(LocalDateTime.now());
        route.setUpdatedAt(LocalDateTime.now());
         routesRepository.save(route);
         return modelMapper.map(route, RoutesResponse.class);
    }

    @Override
    public List<RoutesResponse> getAllRoutes() {
        List<Routes> list =  routesRepository.findAll();
        return list.stream()
                .map(route -> modelMapper.map(route, RoutesResponse.class))
                .toList();
    }

    @Override
    public Optional<RoutesResponse> getRouteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Route ID cannot be null");
        }
        Optional<Routes> optional = routesRepository.findById(id);
        return optional.map(route -> modelMapper.map(route, RoutesResponse.class));
    }

    @Override
    public List<RoutesResponse> getRoutesByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Route name cannot be null or empty");
        }
        List<Routes> list = routesRepository.findByRouteNameContainingIgnoreCase(name.trim());
        return list.stream()
                .map(route -> modelMapper.map(route, RoutesResponse.class))
                .toList();
    }
    @Transactional
    @Override
    public RoutesResponse updateRoute(Long id, RoutesRequest routeUpdate) {
            if (id == null) {
                throw new IllegalArgumentException("Route ID cannot be null");
            }

            Routes existingRoute = routesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Route not found with id: " + id));

            if (routeUpdate.getRouteCode() != null &&
                    !existingRoute.getRouteCode().equals(routeUpdate.getRouteCode()) &&
                    routesRepository.existsByRouteCode(routeUpdate.getRouteCode())) {
                throw new RuntimeException("Route code already exists: " + routeUpdate.getRouteCode());
            }

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

             routesRepository.save(existingRoute);
             return modelMapper.map(existingRoute, RoutesResponse.class);
        }

    @Transactional
    @Override
    public void deleteRoute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Route ID cannot be null");
        }

        if (!routesRepository.existsById(id)) {
            throw new EntityNotFoundException("Route not found with id: " + id);
        }

        // Check if route has stations before deleting
        Optional<Routes> route = routesRepository.findById(id);
        if (!route.isPresent()) {
            throw  new EntityNotFoundException("Route not found with id: " + id);
        }
        route.get().setDeleted(true);
        List<StationRoute> stationRoutes = stationRouteRepository.findByRoute_RouteIdAndIsDeletedOrderBySequenceOrder(id, false);
        for (StationRoute stationRoute : stationRoutes) {
            stationRouteService.deleteStationRoute(stationRoute.getId());
        }
        routesRepository.save(route.get());
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        return routesRepository.existsById(id);
    }

    @Override
    public Optional<RoutesResponse> getRouteByCode(String routeCode) {
        if (routeCode == null || routeCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Route code cannot be null or empty");
        }
        Optional<Routes> optional = routesRepository.findByRouteCode(routeCode.trim());
        return optional.map(route -> modelMapper.map(route, RoutesResponse.class));
        }
}
