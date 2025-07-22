package com.example.stationservice.repository;

import com.example.stationservice.model.Routes;
import com.example.stationservice.model.StationRoute;
import com.example.stationservice.model.Stations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StationRouteRepository extends JpaRepository<StationRoute, Long> {
    List<StationRoute> findByRouteRouteId(Long routeRouteId);

    List<StationRoute> findByStation_StationIdAndIsDeleted(Long stationStationId, boolean stationDeleted);
    List<StationRoute> findByRoute_RouteIdAndIsDeletedOrderBySequenceOrder(Long routeRouteId, boolean stationDeleted);
    StationRoute findByRoute_RouteIdAndStation_StationIdAndIsDeleted(Long routeRouteId, Long stationStationId, boolean stationDeleted);

}
