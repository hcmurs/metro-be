package com.example.stationservice.service;

import com.example.stationservice.dto.StationsRequest;
import com.example.stationservice.model.Stations;

import java.util.List;
import java.util.Optional;

public interface StationsService {
    // Create
    Stations createStation(StationsRequest station);

    // Read
    List<Stations> getAllStations();
    Optional<Stations> getStationById(Long id);
    List<Stations> getStationsByName(String name);

    // Update
    Stations updateStation(Long id, Stations station);

    // Delete
    void deleteStation(Long id);
    boolean checkStationOnLine(Long startStationId, Long endStationId,Long thisStation);
    // Additional utility methods
    boolean existsById(Long id);
    List<Stations> getStationsByRouteId(Long routeId);
}
