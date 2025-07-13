package com.example.stationservice.service;

import com.example.stationservice.dto.Response;
import com.example.stationservice.model.BusRoutes;
import com.example.stationservice.model.BusStationRoutes;
import com.example.stationservice.model.BusStations;
import com.example.stationservice.repository.BusRoutesRepository;
import com.example.stationservice.repository.BusStationRoutesRepository;
import com.example.stationservice.repository.BusStationsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusStationServiceImpl implements BusStationsService {
    private final BusStationsRepository busStationsRepository;
    private final BusRoutesRepository busRoutesRepository;
    private final BusStationRoutesRepository busStationRoutesRepository;
    @Override
    public List<BusStations> getBusStations() {
        return busStationsRepository.findAll();
    }

    @Override
    public BusStations getBusStationById(String id) {
        return busStationsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bus Station not found with id: " + id));
    }

    @Override
    public BusStations createBusStation(BusStations busStation) {
        return busStationsRepository.save(busStation);
    }

    @Override
    public BusStations updateBusStation(String id, BusStations updated) {
        Optional<BusStations> optional = busStationsRepository.findById(id);
        if (optional.isPresent()) {
            BusStations existing = optional.get();
            existing.setName(updated.getName());
            existing.setLatitude(updated.getLatitude());
            existing.setLongitude(updated.getLongitude());
            existing.setIsActive(updated.getIsActive());
            existing.setAddress(updated.getAddress());
            existing.setCode(updated.getCode());
            return busStationsRepository.save(existing);
        } else  {
            throw new EntityNotFoundException("Bus Station not found with id: " + id);
        }
    }

    @Override
    public void deleteBusStation(String id) {
        busStationsRepository.deleteById(id);
    }

    @Override
    public List<BusRoutes> getBusRoutes() {
        return busRoutesRepository.findAll();
    }

    @Override
    public List<BusRoutes> getBusRoutesByStationId(String stationId) {
        return List.of();
    }

    @Override
    public List<BusStationRoutes> getBusStationRoutes() {
        return busStationRoutesRepository.findAll();
    }

    @Override
    public Response.BusStationResponse getBusStationRoutesStationId(String stationId) {
        BusStations busStation = busStationsRepository.findById(stationId)
                .orElseThrow(() -> new EntityNotFoundException("Bus Station not found with id: " + stationId));
        List<BusStationRoutes> busStationRoutes = busStationRoutesRepository.findByStationId(stationId);
        List<BusRoutes> busRoutes = new ArrayList<>();
        for (BusStationRoutes busStationRoute : busStationRoutes) {
            // Add logging to see what ID is being searched
            String routeId = busStationRoute.getRouteId();
            System.out.println("Searching for route with ID: " + routeId);
// Optional: directly query to check what's available
            System.out.println("Available route IDs: " + busRoutesRepository.findAll().stream()
                    .map(BusRoutes::getId)
                    .toList());
             Optional<BusRoutes> route = busRoutesRepository.findById(busStationRoute.getRouteId());
            if (route.isEmpty()) {
                throw new EntityNotFoundException("Route not found with id: " + busStationRoute.getRouteId());
            }
             busRoutes.add(route.get());
        }
        return
                Response.BusStationResponse.builder()
                        .id(busStation.getId())
                        .name(busStation.getName())
                        .latitude(busStation.getLatitude())
                        .longitude(busStation.getLongitude())
                        .isActive(busStation.getIsActive())
                        .address(busStation.getAddress())
                        .code(busStation.getCode())
                        .routes(busRoutes)
                        .build();
    }
}
