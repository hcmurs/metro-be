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

import com.example.stationservice.dto.RoutesRequest;
import com.example.stationservice.dto.RoutesResponse;
import java.util.List;
import java.util.Optional;

public interface RoutesService {
  RoutesResponse createRoute(RoutesRequest request);

  List<RoutesResponse> getAllRoutes();

  Optional<RoutesResponse> getRouteById(Long id);

  List<RoutesResponse> getRoutesByName(String name);

  RoutesResponse updateRoute(Long id, RoutesRequest route);

  void deleteRoute(Long id);

  boolean existsById(Long id);

  // Additional methods
  Optional<RoutesResponse> getRouteByCode(String routeCode);
}
