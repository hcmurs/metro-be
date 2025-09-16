/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.service;

import java.util.List;
import org.alfred.ticketservice.dto.fare_matrix.*;

public interface FareMatrixService {
  FareMatrixResponse getFareMatrixById(Long id);

  FareMatrixResponse createFareMatrix(FareMatrixRequest fareMatrix);

  FareMatrixResponse updateFareMatrix(FareMatrixRequest fareMatrix, Long id);

  void deleteFareMatrix(Long id);

  FareMatrixResponse updateStatusFare(Long id, boolean status);

  List<FareMatrixResponse> getAllFareMatrices();

  List<FareMatrixResponse> getFareMatricesByStartStationId(Long startStationId);

  List<FareMatrixResponse> getFareMatricesByEndStationIdorStartId(Long StationId);

  boolean isStationInFareMatrix(Long stationId, Long fareMatrixId);

  FareMatrixResponse getFareMatrixByStations(FindFareRequest request);
}
