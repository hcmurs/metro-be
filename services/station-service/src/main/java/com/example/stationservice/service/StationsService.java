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

import com.example.stationservice.dto.StationsRequest;
import com.example.stationservice.dto.StationsResponse;
import com.example.stationservice.model.Stations;
import java.util.List;
import java.util.Optional;

public interface StationsService {
  // Create
  StationsResponse createStation(StationsRequest station);

  // Read
  List<StationsResponse> getAllStations();

  Optional<StationsResponse> getStationById(Long id);

  List<StationsResponse> getStationsByName(String name);

  // Update
  StationsResponse updateStation(Long id, StationsRequest station);

  // Delete
  void deleteStation(Long id);

  //    boolean checkStationOnLine(Long startStationId, Long endStationId,Long thisStation);
  // Additional utility methods
  boolean existsById(Long id);

  //    List<StationsResponse> getStationsByRouteId(Long routeId);
  StationsResponse updateStationStatus(Long id, Stations.Status status);
}
