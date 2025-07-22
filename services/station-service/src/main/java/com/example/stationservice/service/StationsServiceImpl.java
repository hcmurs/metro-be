package com.example.stationservice.service;

import com.example.stationservice.dto.StationRouteRequest;
import com.example.stationservice.dto.StationsRequest;
import com.example.stationservice.dto.StationsResponse;
import com.example.stationservice.model.Routes;
import com.example.stationservice.model.StationRoute;
import com.example.stationservice.model.Stations;
import com.example.stationservice.repository.RoutesRepository;
import com.example.stationservice.repository.StationRouteRepository;
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
    private  StationRouteService stationRouteService;
    @Autowired
    private StationRouteRepository stationRouteRepository;
    @Transactional
    @Override
    public StationsResponse createStation(StationsRequest request) {
        if (stationsRepository.existsByStationCode(request.getStationCode())) {
            throw new EntityExistsException("Station code already exists: " + request.getStationCode());
        }
        Stations station = new Stations();
        station.setStationCode(request.getStationCode());
        station.setName(request.getName());
        station.setAddress(request.getAddress());
        station.setLatitude(request.getLatitude());
        station.setLongitude(request.getLongitude());
        station.setDeleted(false);
        System.out.println("Station created: " + station);
         return mapToStationsResponse(stationsRepository.save(station));
    }

    @Override
    public List<StationsResponse> getAllStations() {

        List<Stations> list = stationsRepository.findAllByIsDeletedFalse();
        return list.stream()
                .map(this::mapToStationsResponse)
                .toList();
    }

    @Override
    public Optional<StationsResponse> getStationById(Long id) {
        if (id == null) {
            throw new EntityNotFoundException("Station ID cannot be null");
        }
        Optional<Stations> optional = stationsRepository.findById(id);
    return optional.map(this::mapToStationsResponse);}


    @Override
    public List<StationsResponse> getStationsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Station name cannot be null or empty");
        }
        List<Stations> list =  stationsRepository.findByNameContainingIgnoreCase(name.trim());
        return list.stream()
                .map(this::mapToStationsResponse)
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
        existingStation.setUpdatedAt(LocalDateTime.now());
         return mapToStationsResponse(stationsRepository.save(existingStation));
    }
    @Transactional
    @Override
    public void deleteStation(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Station ID cannot be null");
        }
        Stations station = stationsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Station not found with id: " + id));
        station.setDeleted(true);
        List<StationRoute> stationRoutes = stationRouteRepository.findByStation_StationIdAndIsDeleted(id, false);
        for (StationRoute stationRoute : stationRoutes) {
            stationRouteService.deleteStationRoute(stationRoute.getId());
        }
        stationsRepository.save(station);
    }


//    @Override
//    public boolean checkStationOnLine(Long startStationId, Long endStationId, Long thisStation) {
//        if (startStationId == null || endStationId == null || thisStation == null) {
//            throw new IllegalArgumentException("Station IDs cannot be null");
//        }
//        Stations startStations = stationsRepository.findById(startStationId)
//                .orElseThrow(() -> new EntityNotFoundException("Start station not found with id: " + startStationId));
//        Stations endStations = stationsRepository.findById(endStationId)
//                .orElseThrow(() -> new EntityNotFoundException("End station not found with id: " + endStationId));
//        Stations thisStationObj = stationsRepository.findById(thisStation)
//                .orElseThrow(() -> new EntityNotFoundException("This station not found with id: " + thisStation));
//        if(startStations.getSequenceOrder() < endStations.getSequenceOrder()) {
//            if (thisStationObj.getSequenceOrder() >= startStations.getSequenceOrder() &&
//                    thisStationObj.getSequenceOrder() <= endStations.getSequenceOrder()) {
//                return true;
//            }
//        }
//        else if(startStations.getSequenceOrder() > endStations.getSequenceOrder()) {
//            if (thisStationObj.getSequenceOrder() >= endStations.getSequenceOrder() &&
//                    thisStationObj.getSequenceOrder() <= startStations.getSequenceOrder()) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        return stationsRepository.existsById(id);    }
//    @Override
//    public List<StationsResponse> getStationsByRouteId(Long routeId) {
//        if (routeId == null) {
//            throw new IllegalArgumentException("Route ID cannot be null");
//        }
//        List <Stations> list = stationsRepository.findByRouteRouteIdOrderBySequenceOrder(routeId);
//            return list.stream()
//                .map(station -> modelMapper.map(station, StationsResponse.class))
//                .toList();
//    }
    @Transactional
    @Override
    public StationsResponse updateStationStatus(Long id, Stations.Status status) {
        if (id == null) {
            throw new EntityNotFoundException("Station ID cannot be null");
        }

        Stations existingStation = stationsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Station not found with id: " + id));

        existingStation.setStatus(status);
        List<StationRoute> stationRoutes = stationRouteRepository.findByStation_StationIdAndIsDeleted(id, false);
        for (StationRoute stationRoute : stationRoutes) {
                stationRouteService.updateStationRouteStatus(stationRoute.getId(), status);
            }
        existingStation.setUpdatedAt(LocalDateTime.now());
         return mapToStationsResponse(stationsRepository.save(existingStation));
    }

    private StationsResponse mapToStationsResponse(Stations stations) {
        return StationsResponse.builder().stationId(stations.getStationId())
                .stationCode(stations.getStationCode())
                .name(stations.getName())
                .address(stations.getAddress())
                .latitude(stations.getLatitude())
                .longitude(stations.getLongitude())
                .status(stations.getStatus())
                .createdAt(stations.getCreatedAt())
                .updatedAt(stations.getUpdatedAt())
                .isDeleted(stations.isDeleted())
                .build();
    }

}
