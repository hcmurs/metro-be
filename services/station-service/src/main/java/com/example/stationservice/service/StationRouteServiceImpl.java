package com.example.stationservice.service;

import com.example.stationservice.dto.StationEvent;
import com.example.stationservice.dto.StationRouteRequest;
import com.example.stationservice.dto.StationRouteResponse;
import com.example.stationservice.dto.StationsResponse;
import com.example.stationservice.messaging.producer.StationEventPublisher;
import com.example.stationservice.model.Routes;
import com.example.stationservice.model.StationRoute;
import com.example.stationservice.model.Stations;
import com.example.stationservice.repository.RoutesRepository;
import com.example.stationservice.repository.StationRouteRepository;
import com.example.stationservice.repository.StationsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class StationRouteServiceImpl implements  StationRouteService {
    private final StationRouteRepository stationRouteRepository;
    private final RoutesRepository routeRepository;
    private final StationsRepository stationsRepository;
    private final StationEventPublisher stationEventPublisher;

    @Override
    public List<StationRouteResponse> getStationRoutesByRouteId(Long routeId) {
        List<StationRoute> routes = stationRouteRepository.findByRoute_RouteIdAndIsDeletedOrderBySequenceOrder(routeId, false);
        return routes.stream().map(this::mapToResponse).toList();
    }

    @Override
    public StationRouteResponse saveStationRoute(StationRouteRequest request) {
        Routes route = routeRepository.findById(request.routeId())
                .orElseThrow(() -> new EntityNotFoundException("Route not found"));

        Stations station = stationsRepository.findById(request.stationId())
                .orElseThrow(() -> new EntityNotFoundException("Station not found"));
        StationRoute stationRouteExist = stationRouteRepository.findByRoute_RouteIdAndStation_StationIdAndIsDeleted(request.routeId(), request.stationId(), false);
        if(stationRouteExist != null) {
            throw new EntityNotFoundException("Station already exists on this route");
        }
        List<StationRoute> existingRoutes = stationRouteRepository
                .findByRoute_RouteIdAndIsDeletedOrderBySequenceOrder(route.getRouteId(), false);

        int insertOrder;

        if (existingRoutes.isEmpty()) {
            insertOrder = 1;
        } else {
            int size = existingRoutes.size();
            int requested = request.sequenceOrder();

            if (requested > size) {
                insertOrder = size + 1;
            } else if (requested < 1) {
                throw new IllegalArgumentException("Invalid request sequence order: " + requested);
            } else {
                insertOrder = requested;
                for (StationRoute existing : existingRoutes) {
                    if (existing.getSequenceOrder() >= insertOrder) {
                        existing.setSequenceOrder(existing.getSequenceOrder() + 1);
                    }
                }
                stationRouteRepository.saveAll(existingRoutes);
            }
        }

        StationRoute stationRoute = new StationRoute();
        stationRoute.setRoute(route);
        stationRoute.setStation(station);
        stationRoute.setSequenceOrder(insertOrder);
        StationRoute saved = stationRouteRepository.save(stationRoute);
        StationEvent event = new StationEvent(
                saved.getId(),
                LocalDateTime.now()
        );
        stationEventPublisher.publishStationAdded(event);
        return mapToResponse(saved);
    }

    @Transactional
    @Override
    public StationRouteResponse updateStationRoute(Long id, StationRouteRequest request) {
        StationRoute stationRoute = stationRouteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StationRoute not found"));
        Routes route = routeRepository.findById(request.routeId())
                .orElseThrow(() -> new EntityNotFoundException("Route not found"));
        Stations station = stationsRepository.findById(request.stationId())
                .orElseThrow(() -> new EntityNotFoundException("Station not found"));
        stationRoute.setRoute(route);
        stationRoute.setStation(station);
        StationRoute updated = stationRouteRepository.save(stationRoute);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteStationRoute(Long id) {
        StationRoute stationRoute = stationRouteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StationRoute not found"));
        Long routeId = stationRoute.getRoute().getRouteId();
        stationRoute.setDeleted(true);
        stationRouteRepository.save(stationRoute);
        reorderStationRouteAfterDelete(routeId);
        StationEvent event = new StationEvent(
                id,
                LocalDateTime.now()
        );

    }

    @Override
    public void reorderStationRouteAfterDelete(Long routeId) {
        List<StationRoute> routes = stationRouteRepository.findByRoute_RouteIdAndIsDeletedOrderBySequenceOrder(routeId, false);
        for (int i = 0; i < routes.size(); i++) {
            StationRoute sr = routes.get(i);
            sr.setSequenceOrder(i + 1);
        }
        stationRouteRepository.saveAll(routes);
    }

    @Transactional
    @Override
    public void swapStationRouteOrder(Long firstId, Long secondId) {
        StationRoute first = stationRouteRepository.findById(firstId)
                .orElseThrow(() -> new EntityNotFoundException("First StationRoute not found"));
        StationRoute second = stationRouteRepository.findById(secondId)
                .orElseThrow(() -> new EntityNotFoundException("Second StationRoute not found"));
        int tempOrder = first.getSequenceOrder();
        first.setSequenceOrder(second.getSequenceOrder());
        second.setSequenceOrder(tempOrder);
        stationRouteRepository.save(first);
        stationRouteRepository.save(second);
    }

    @Override
    public boolean checkStationOnLine(Long startStationId, Long endStationId, Long thisStationId) {
        StationRoute start = stationRouteRepository.findById(startStationId)
                .orElseThrow(() -> new EntityNotFoundException("Start StationRoute not found"));

        StationRoute end = stationRouteRepository.findById(endStationId)
                .orElseThrow(() -> new EntityNotFoundException("End StationRoute not found"));
        StationRoute thisStation = stationRouteRepository.findById(thisStationId)
                .orElseThrow(() -> new EntityNotFoundException("This StationRoute not found"));
        if(!start.getRoute().getRouteId().equals(end.getRoute().getRouteId()) ||
                !start.getRoute().getRouteId().equals(thisStation.getRoute().getRouteId())){
            throw new IllegalArgumentException("Start and End and this StationRoutes must belong to the same route");
        }
        if (start.getSequenceOrder() < end.getSequenceOrder()) {
            return thisStation.getSequenceOrder() >= start.getSequenceOrder() &&
                    thisStation.getSequenceOrder() <= end.getSequenceOrder();
        } else if (start.getSequenceOrder() > end.getSequenceOrder()) {
            return thisStation.getSequenceOrder() >= end.getSequenceOrder() &&
                    thisStation.getSequenceOrder() <= start.getSequenceOrder();
        } else return false;
    }

    @Override
    public void updateStationRouteStatus(Long id, Stations.Status status) {
        StationRoute stationRoute = stationRouteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StationRoute not found"));
        stationRoute.setStatus(status);
        stationRouteRepository.save(stationRoute);
        StationEvent event = new StationEvent(
                stationRoute.getId(),
                LocalDateTime.now()
        );
        stationEventPublisher.publishStationDeleted(event);
    }

    @Override
    public StationRouteResponse getStationRouteById(Long id) {
        StationRoute stationRoute = stationRouteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StationRoute not found"));
        return mapToResponse(stationRoute);
    }

    private StationRouteResponse mapToResponse(StationRoute stationRoute) {
        return  StationRouteResponse.builder(
                ).id(stationRoute.getId())
                .RouteId(stationRoute.getRoute().getRouteId())
                .stationsResponse(mapToStationsResponse(stationRoute.getStation()))
                .sequenceOrder(stationRoute.getSequenceOrder())
                .status(stationRoute.getStatus())
                .createdAt(stationRoute.getCreatedAt())
                .updatedAt(stationRoute.getUpdatedAt())
                .isDeleted(stationRoute.isDeleted())
                .build();
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
