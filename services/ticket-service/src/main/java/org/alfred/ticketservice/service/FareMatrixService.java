package org.alfred.ticketservice.service;

import org.alfred.ticketservice.dto.fare_matrix.*;

import java.util.List;

public interface FareMatrixService {
    FareMatrixResponse getFareMatrixById(Long id);

    FareMatrixResponse createFareMatrix(FareMatrixRequest fareMatrix);

    FareMatrixResponse updateFareMatrix(FareMatrixRequest fareMatrix, Long id);

    void deleteFareMatrix(Long id);

    FareMatrixResponse updateStatusFare(Long id,boolean status);

    List<FareMatrixResponse> getAllFareMatrices();

    List<FareMatrixResponse> getFareMatricesByStartStationId(Long startStationId);

    List<FareMatrixResponse> getFareMatricesByEndStationIdorStartId(Long StationId);

    boolean isStationInFareMatrix(Long stationId, Long fareMatrixId);

    FareMatrixResponse getFareMatrixByStations(FindFareRequest request);
}
