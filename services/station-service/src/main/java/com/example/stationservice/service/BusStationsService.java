/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Station-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
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
