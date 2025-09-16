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

import com.example.stationservice.dto.StationRouteRequest;
import com.example.stationservice.dto.StationRouteResponse;
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
