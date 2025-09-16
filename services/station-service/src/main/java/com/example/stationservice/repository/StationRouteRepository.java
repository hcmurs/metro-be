/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Station-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.stationservice.repository;

import com.example.stationservice.model.StationRoute;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRouteRepository extends JpaRepository<StationRoute, Long> {
  List<StationRoute> findByRouteRouteId(Long routeRouteId);

  List<StationRoute> findByStation_StationIdAndIsDeleted(
      Long stationStationId, boolean stationDeleted);

  List<StationRoute> findByRoute_RouteIdAndIsDeletedOrderBySequenceOrder(
      Long routeRouteId, boolean stationDeleted);

  StationRoute findByRoute_RouteIdAndStation_StationIdAndIsDeleted(
      Long routeRouteId, Long stationStationId, boolean stationDeleted);
}
