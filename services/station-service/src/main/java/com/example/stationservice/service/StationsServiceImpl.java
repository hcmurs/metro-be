package com.example.stationservice.service;

import com.example.stationservice.dto.StationsRequest;
import com.example.stationservice.dto.StationsResponse;
import com.example.stationservice.model.Routes;
import com.example.stationservice.model.Stations;
import com.example.stationservice.repository.RoutesRepository;
import com.example.stationservice.repository.StationsRepository;
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
public class StationsServiceImpl implements StationsService {
    @Autowired
    private StationsRepository stationsRepository;
    @Autowired
    private RoutesRepository routesRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Transactional
    @Override
    public StationsResponse createStation(StationsRequest request) {
        Routes route = routesRepository.findById(request.getRouteId())
                .orElseThrow(() -> new EntityNotFoundException("Route not found with id: " + request.getRouteId()));

        if (stationsRepository.existsByStationCode(request.getStationCode())) {
            throw new EntityExistsException("Station code already exists: " + request.getStationCode());
        }
        if( request.getSequenceOrder() == null || request.getSequenceOrder() < 0 || stationsRepository.existsBySequenceOrder(request.getSequenceOrder())) {
            throw new IllegalArgumentException("Invalid sequence order: " + request.getSequenceOrder());
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
         stationsRepository.save(station);
         return modelMapper.map(station, StationsResponse.class);
    }

    @Override
    public List<StationsResponse> getAllStations() {

        List<Stations> list = stationsRepository.findAll();
        return list.stream()
                .map(station -> modelMapper.map(station, StationsResponse.class))
                .toList();
    }

    @Override
    public Optional<StationsResponse> getStationById(Long id) {
        if (id == null) {
            throw new EntityNotFoundException("Station ID cannot be null");
        }
        Optional<Stations> optional = stationsRepository.findById(id);
    return optional.map(station -> modelMapper.map(station, StationsResponse.class));}


    @Override
    public List<StationsResponse> getStationsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Station name cannot be null or empty");
        }
        List<Stations> list =  stationsRepository.findByNameContainingIgnoreCase(name.trim());
        return list.stream()
                .map(station -> modelMapper.map(station, StationsResponse.class))
                .toList();
    }
    @Transactional
    @Override
    public StationsResponse updateStation(Long id, StationsRequest stationUpdate) {
        if (id == null) {
            throw new EntityNotFoundException("Station ID cannot be null");
        }

        Stations existingStation = stationsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Station not found with id: " + id));

        if (stationUpdate.getStationCode() != null) {
            if (!existingStation.getStationCode().equals(stationUpdate.getStationCode()) &&
                    stationsRepository.existsByStationCode(stationUpdate.getStationCode())) {
                throw new EntityNotFoundException("Station code already exists: " + stationUpdate.getStationCode());
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

        if (stationUpdate.getRouteId() != null &&
                (existingStation.getRoute() == null ||
                        !existingStation.getRoute().getRouteId().equals(stationUpdate.getRouteId()))) {

            Routes route = routesRepository.findById(stationUpdate.getRouteId())
                    .orElseThrow(() -> new EntityNotFoundException("Route not found with id: " + stationUpdate.getRouteId()));
            existingStation.setRoute(route);
        }



        existingStation.setUpdatedAt(LocalDateTime.now());

         stationsRepository.save(existingStation);
         return modelMapper.map(existingStation, StationsResponse.class);
    }
    @Transactional
    @Override
    public void deleteStation(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Station ID cannot be null");
        }

        if (!stationsRepository.existsById(id)) {
            throw new EntityNotFoundException("Station not found with id: " + id);
        }

        Optional<Stations> station = stationsRepository.findById(id);
        if (station.isPresent() && station.get().getSchedules() != null && !station.get().getSchedules().isEmpty()) {
            throw new RuntimeException("Cannot delete station with existing schedules. Delete schedules first.");
        }

        stationsRepository.deleteById(id);

    }

    @Override
    public boolean checkStationOnLine(Long startStationId, Long endStationId, Long thisStation) {
        if (startStationId == null || endStationId == null || thisStation == null) {
            throw new IllegalArgumentException("Station IDs cannot be null");
        }
        Stations startStations = stationsRepository.findById(startStationId)
                .orElseThrow(() -> new EntityNotFoundException("Start station not found with id: " + startStationId));
        Stations endStations = stationsRepository.findById(endStationId)
                .orElseThrow(() -> new EntityNotFoundException("End station not found with id: " + endStationId));
        Stations thisStationObj = stationsRepository.findById(thisStation)
                .orElseThrow(() -> new EntityNotFoundException("This station not found with id: " + thisStation));
        if(startStations.getSequenceOrder() < endStations.getSequenceOrder()) {
            if (thisStationObj.getSequenceOrder() >= startStations.getSequenceOrder() &&
                    thisStationObj.getSequenceOrder() <= endStations.getSequenceOrder()) {
                return true;
            }
        }
        else if(startStations.getSequenceOrder() > endStations.getSequenceOrder()) {
            if (thisStationObj.getSequenceOrder() >= endStations.getSequenceOrder() &&
                    thisStationObj.getSequenceOrder() <= startStations.getSequenceOrder()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        return stationsRepository.existsById(id);    }
    @Override
    public List<StationsResponse> getStationsByRouteId(Long routeId) {
        if (routeId == null) {
            throw new IllegalArgumentException("Route ID cannot be null");
        }
        List <Stations> list = stationsRepository.findByRouteRouteIdOrderBySequenceOrder(routeId);
            return list.stream()
                .map(station -> modelMapper.map(station, StationsResponse.class))
                .toList();
    }
    @Transactional
    @Override
    public StationsResponse updateStationStatus(Long id, Stations.Status status) {
        if (id == null) {
            throw new EntityNotFoundException("Station ID cannot be null");
        }

        Stations existingStation = stationsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Station not found with id: " + id));

        existingStation.setStatus(status);
        existingStation.setUpdatedAt(LocalDateTime.now());

         stationsRepository.save(existingStation);
         return modelMapper.map(existingStation, StationsResponse.class);
    }

}
