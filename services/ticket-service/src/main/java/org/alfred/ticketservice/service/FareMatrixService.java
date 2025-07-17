package org.alfred.ticketservice.service;

import java.util.List;
import org.alfred.ticketservice.dto.fare_matrix.FareMatrixRequest;
import org.alfred.ticketservice.dto.fare_matrix.FareMatrixResponse;
import org.alfred.ticketservice.dto.fare_matrix.FindFareRequest;

public interface FareMatrixService {
    FareMatrixResponse getFareMatrixById(Long id);

    FareMatrixResponse createFareMatrix(FareMatrixRequest fareMatrix);

    FareMatrixResponse updateFareMatrix(FareMatrixRequest fareMatrix, Long id);

    void deleteFareMatrix(Long id);

    List<FareMatrixResponse> getAllFareMatrices();

    List<FareMatrixResponse> getFareMatricesByStartStationId(Long startStationId);

    boolean isStationInFareMatrix(Long stationId, Long fareMatrixId);

    FareMatrixResponse getFareMatrixByStations(FindFareRequest request);
}
