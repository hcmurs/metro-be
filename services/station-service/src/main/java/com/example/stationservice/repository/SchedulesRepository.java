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

import com.example.stationservice.model.Schedules;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulesRepository extends JpaRepository<Schedules, Long> {
  List<Schedules> findByStationRouteId(Long stationRouteId);

  List<Schedules> findByStationRoute_IdOrderByTimeArrival(Long stationStationId);
  // List<Schedules> findByStationId(Integer stationId);

}
