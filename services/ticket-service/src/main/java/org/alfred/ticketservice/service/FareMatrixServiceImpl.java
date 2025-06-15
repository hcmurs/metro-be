package org.alfred.ticketservice.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.alfred.ticketservice.client.StationClient;
import org.alfred.ticketservice.dto.fare_matrix.*;
import org.alfred.ticketservice.model.FareMatrix;
import org.alfred.ticketservice.repository.FareMatrixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FareMatrixServiceImpl implements FareMatrixService{
    @Autowired
    FareMatrixRepository fareMatrixRepository;

    @Autowired
    StationClient stationClient;


    @Override
    public FareMatrixResponse getFareMatrixById(Long id) {
        FareMatrix fareMatrix = fareMatrixRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fare matrix not found"));
        return mapToResponse(fareMatrix);
    }

    @Override
    public FareMatrixResponse createFareMatrix(FareMatrixRequest fareMatrix) {
        FareMatrix fareMatrixexist = fareMatrixRepository.findByStartStationIdAndEndStationId(
                fareMatrix.startStationId(), fareMatrix.endStationId());
        if (fareMatrixexist != null) throw new EntityExistsException("Fare matrix already exists for this station pair");
        FareMatrix fareMatrixEntity = FareMatrix.builder()
                .price(fareMatrix.price())
                .startStationId(fareMatrix.startStationId())
                .endStationId(fareMatrix.endStationId())
                .name(fareMatrix.name())
                .isActive(true)
                .build();
        fareMatrixEntity = fareMatrixRepository.save(fareMatrixEntity);
        return mapToResponse(fareMatrixEntity);
    }

    @Override
    public FareMatrixResponse updateFareMatrix(FareMatrixUpdateRequest fareMatrix) {
        FareMatrix fareMatrixEntity = fareMatrixRepository.findById(fareMatrix.fareMatrixId())
                .orElseThrow(() -> new EntityNotFoundException("Fare matrix not found"));
        fareMatrixEntity.setPrice(fareMatrix.price());
        fareMatrixEntity.setName(fareMatrix.name());
        fareMatrixEntity.setActive(fareMatrix.isActive());
        fareMatrixRepository.save(fareMatrixEntity);
        return mapToResponse(fareMatrixEntity);
    }

    @Override
    public void deleteFareMatrix(Long id) {
        FareMatrix fareMatrix = fareMatrixRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fare matrix not found"));
        fareMatrix.setActive(false);
        fareMatrixRepository.save(fareMatrix);
    }

    @Override
    public List<FareMatrixResponse> getAllFareMatrices() {
        List<FareMatrix> fareMatrices = fareMatrixRepository.findAll();
        return fareMatrices.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<FareMatrixResponse> getFareMatricesByStartStationId(Long startStationId) {
        if (stationClient.checkStationExists(startStationId).getData() ==null || !stationClient.checkStationExists(startStationId).getData()) {
            throw new EntityNotFoundException("Station not found with id: " + startStationId);
        }
        List<FareMatrix> fareMatrices = (List<FareMatrix>) fareMatrixRepository.findByStartStationId(startStationId);
        return fareMatrices.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public boolean isStationInFareMatrix(Long stationId, Long fareMatrixId) {
        FareMatrix fareMatrixEntity = fareMatrixRepository.findById(fareMatrixId)
                .orElseThrow(() -> new EntityNotFoundException("Fare matrix not found"));
        if (stationClient.checkStationExists(stationId).getData() == null || !stationClient.checkStationExists(stationId).getData() ) {
            throw new EntityNotFoundException("Station not found with id: " + stationId);
        }
        long startStationId = fareMatrixEntity.getStartStationId();
        long endStationId = fareMatrixEntity.getEndStationId();
        if(stationClient.checkStationOnLine(startStationId,endStationId, stationId).getData() == null) {
            throw new EntityNotFoundException("Station not found on the line between start and end stations");
        }
        return stationClient.checkStationOnLine(startStationId,endStationId, stationId).getData();
    }

    private FareMatrixResponse mapToResponse(FareMatrix fareMatrix) {
        return FareMatrixResponse.builder()
                .fareMatrixId(fareMatrix.getFareMatrixId())
                .price(fareMatrix.getPrice())
                .name(fareMatrix.getName())
                .startStationId(fareMatrix.getStartStationId())
                .endStationId(fareMatrix.getEndStationId())
                .createdAt(fareMatrix.getCreatedAt())
                .updatedAt(fareMatrix.getUpdatedAt())
                .build();
    }
}
