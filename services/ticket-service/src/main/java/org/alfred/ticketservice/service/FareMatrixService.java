package org.alfred.ticketservice.service;

import org.alfred.ticketservice.dto.fare_matrix.*;

import java.util.List;

public interface FareMatrixService {
    FareMatrixResponse getFareMatrixById(Long id);

    FareMatrixResponse createFareMatrix(FareMatrixRequest fareMatrix);

    FareMatrixResponse updateFareMatrix(FareMatrixUpdateRequest fareMatrix);

    void deleteFareMatrix(Long id);

    List<FareMatrixResponse> getAllFareMatrices();

    List<FareMatrixResponse> getFareMatricesByStartStationId(Long startStationId);

    boolean isStationInFareMatrix(Long stationId, Long fareMatrixId);

    FareMatrixResponse getFareMatrixByStations(FindFareRequest request);
}
