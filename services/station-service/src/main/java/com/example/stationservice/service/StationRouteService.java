package com.example.stationservice.service;

import com.example.stationservice.dto.StationRouteRequest;
import com.example.stationservice.dto.StationRouteResponse;
import com.example.stationservice.model.StationRoute;
import com.example.stationservice.model.Stations;

import java.util.List;

public interface StationRouteService {
    List<StationRouteResponse> getStationRoutesByRouteId(Long routeId);
    StationRouteResponse saveStationRoute(StationRouteRequest stationRoute);
    StationRouteResponse updateStationRoute(Long id, StationRouteRequest stationRoute);
    void reorderStationRouteAfterDelete(Long routeId);
    void deleteStationRoute(Long id);
    void swapStationRouteOrder(Long firstStationRouteId, Long secondStationRouteId);
    boolean checkStationOnLine(Long startStationId, Long endStationId, Long thisStationId);
    void updateStationRouteStatus(Long id, Stations.Status status);
    StationRouteResponse getStationRouteById(Long id);

}
