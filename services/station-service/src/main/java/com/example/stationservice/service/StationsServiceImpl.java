package com.example.stationservice.service;

import com.example.stationservice.dto.StationsRequest;
import com.example.stationservice.model.Routes;
import com.example.stationservice.model.Stations;
import com.example.stationservice.repository.RoutesRepository;
import com.example.stationservice.repository.StationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class StationsServiceImpl implements StationsService {
    @Autowired
    private StationsRepository stationsRepository;
    @Autowired
    private RoutesRepository routesRepository;
    @Override
    public Stations createStation(StationsRequest request) {
        Routes route = routesRepository.findById(request.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found with id: " + request.getRouteId()));
        // Check if station code already exists
        if (stationsRepository.existsByStationCode(request.getStationCode())) {
            throw new RuntimeException("Station code already exists: " + request.getStationCode());
        }
        Stations station = new Stations();
        station.setStationCode(request.getStationCode());
        station.setName(request.getName());
        station.setAddress(request.getAddress());
        station.setLatitude(request.getLatitude());
        station.setLongitude(request.getLongitude());
        station.setSequenceOrder(request.getSequenceOrder());
        station.setCreatedAt(LocalDateTime.now());
        station.setUpdatedAt(LocalDateTime.now());
        station.setRoute(route);
        return stationsRepository.save(station);
    }

    @Override
    public List<Stations> getAllStations() {
        return stationsRepository.findAll();
    }

    @Override
    public Optional<Stations> getStationById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Station ID cannot be null");
        }
        return stationsRepository.findById(id);    }
//    }

    @Override
    public List<Stations> getStationsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Station name cannot be null or empty");
        }
        return stationsRepository.findByNameContainingIgnoreCase(name.trim());
    }

    @Override
    public Stations updateStation(Long id, Stations stationUpdate) {
        if (id == null) {
            throw new IllegalArgumentException("Station ID cannot be null");
        }

        Stations existingStation = stationsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Station not found with id: " + id));

        // Update fields if they are not null
        if (stationUpdate.getStationCode() != null) {
            // Check if new station code already exists (excluding current station)
            if (!existingStation.getStationCode().equals(stationUpdate.getStationCode()) &&
                    stationsRepository.existsByStationCode(stationUpdate.getStationCode())) {
                throw new RuntimeException("Station code already exists: " + stationUpdate.getStationCode());
            }
            existingStation.setStationCode(stationUpdate.getStationCode());
        }

        if (stationUpdate.getName() != null && !stationUpdate.getName().trim().isEmpty()) {
            existingStation.setName(stationUpdate.getName().trim());
        }

        if (stationUpdate.getAddress() != null && !stationUpdate.getAddress().trim().isEmpty()) {
            existingStation.setAddress(stationUpdate.getAddress().trim());
        }

        if (stationUpdate.getLatitude() != null) {
            existingStation.setLatitude(stationUpdate.getLatitude());
        }

        if (stationUpdate.getLongitude() != null) {
            existingStation.setLongitude(stationUpdate.getLongitude());
        }

        if (stationUpdate.getSequenceOrder() != null) {
            existingStation.setSequenceOrder(stationUpdate.getSequenceOrder());
        }

        if (stationUpdate.getRoute() != null && stationUpdate.getRoute().getRouteId() != null) {
            Routes route = routesRepository.findById(stationUpdate.getRoute().getRouteId())
                    .orElseThrow(() -> new RuntimeException("Route not found with id: " + stationUpdate.getRoute().getRouteId()));
            existingStation.setRoute(route);
        }

        if (stationUpdate.getStatus() != null) {
            existingStation.setStatus(stationUpdate.getStatus());
        }

        existingStation.setUpdatedAt(LocalDateTime.now());

        return stationsRepository.save(existingStation);
    }

    @Override
    public void deleteStation(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Station ID cannot be null");
        }

        if (!stationsRepository.existsById(id)) {
            throw new RuntimeException("Station not found with id: " + id);
        }

        // Check if station has schedules before deleting
        Optional<Stations> station = stationsRepository.findById(id);
        if (station.isPresent() && station.get().getSchedules() != null && !station.get().getSchedules().isEmpty()) {
            throw new RuntimeException("Cannot delete station with existing schedules. Delete schedules first.");
        }

        stationsRepository.deleteById(id);

    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        return stationsRepository.existsById(id);    }
    @Override
    public List<Stations> getStationsByRouteId(Long routeId) {
        if (routeId == null) {
            throw new IllegalArgumentException("Route ID cannot be null");
        }
        return stationsRepository.findByRouteRouteIdOrderBySequenceOrder(routeId);
    }

}
