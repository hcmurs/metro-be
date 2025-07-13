package com.example.stationservice.service;

import com.example.stationservice.dto.Response;
import com.example.stationservice.model.BusRoutes;
import com.example.stationservice.model.BusStationRoutes;
import com.example.stationservice.model.BusStations;

import java.util.List;

public interface BusStationsService {
    List<BusStations> getBusStations();
    BusStations getBusStationById(String id);
    BusStations createBusStation(BusStations busStation);
    BusStations updateBusStation(String id, BusStations busStation);
    void deleteBusStation(String id);
    List<BusRoutes> getBusRoutes();
    List<BusRoutes> getBusRoutesByStationId(String stationId);
    List<BusStationRoutes> getBusStationRoutes();
    Response.BusStationResponse getBusStationRoutesStationId(String stationId);

}
