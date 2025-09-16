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

import com.example.stationservice.dto.SchedulesRequest;
import com.example.stationservice.dto.SchedulesResponse;
import java.util.List;
import java.util.Optional;

public interface SchedulesService {
  SchedulesResponse createSchedule(SchedulesRequest request);

  List<SchedulesResponse> getAllSchedules();

  Optional<SchedulesResponse> getScheduleById(Long id);

  List<SchedulesResponse> getSchedulesByStationId(Long stationId);

  SchedulesResponse updateSchedule(Long id, SchedulesRequest schedule);

  void deleteSchedule(Long id);

  boolean existsById(Long id);
}
