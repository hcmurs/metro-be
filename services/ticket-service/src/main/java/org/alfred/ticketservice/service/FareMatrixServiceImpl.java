package org.alfred.ticketservice.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.alfred.ticketservice.client.StationClient;
import org.alfred.ticketservice.config.ApiResponse;
import org.alfred.ticketservice.dto.fare_matrix.*;
import org.alfred.ticketservice.dto.station.StationResponse;
import org.alfred.ticketservice.dto.station.StationRouteResponse;
import org.alfred.ticketservice.model.FareMatrix;
import org.alfred.ticketservice.model.FarePricing;
import org.alfred.ticketservice.model.enums.StationStatus;
import org.alfred.ticketservice.repository.FareMatrixRepository;
import org.alfred.ticketservice.repository.FarePricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class FareMatrixServiceImpl implements FareMatrixService{
    @Autowired
    FareMatrixRepository fareMatrixRepository;

    @Autowired
    StationClient stationClient;

    @Autowired
    FarePricingRepository farePricingRepository;


    @Override
    public FareMatrixResponse getFareMatrixById(Long id) {
        FareMatrix fareMatrix = fareMatrixRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fare matrix not found"));
        return mapToResponse(fareMatrix);
    }

    @Override
    public FareMatrixResponse createFareMatrix(FareMatrixRequest fareMatrix) {
        FareMatrix fareMatrixexist = fareMatrixRepository.findByStartStationIdAndEndStationIdAndIsActiveTrue(
                fareMatrix.startStationId(), fareMatrix.endStationId());
        if (fareMatrixexist != null ) throw new EntityExistsException("Fare matrix already exists for this station pair");
        FareMatrix fareMatrixEntity = FareMatrix.builder()
                .distanceInKm(calculateDistanceInKm(fareMatrix.startStationId(), fareMatrix.endStationId()))
                .startStationId(fareMatrix.startStationId())
                .endStationId(fareMatrix.endStationId())
                .farePricing(calculateDistanceInMiles(fareMatrix.startStationId(), fareMatrix.endStationId()))
                .name(fareMatrix.name())
                .isActive(fareMatrix.isActive())
                .build();
        fareMatrixEntity = fareMatrixRepository.save(fareMatrixEntity);
        return mapToResponse(fareMatrixEntity);
    }

    @Override
    public FareMatrixResponse updateFareMatrix(FareMatrixRequest fareMatrix, Long id) {
        FareMatrix fareMatrixEntity = fareMatrixRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fare matrix not found"));
        fareMatrixEntity.setDistanceInKm(calculateDistanceInKm(fareMatrix.startStationId(), fareMatrix.endStationId()));
        fareMatrixEntity.setStartStationId(fareMatrix.startStationId());
        fareMatrixEntity.setFarePricing(calculateDistanceInMiles(fareMatrix.startStationId(), fareMatrix.endStationId()));
        fareMatrixEntity.setEndStationId(fareMatrix.endStationId());
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
    public FareMatrixResponse updateStatusFare(Long id, boolean status) {
        FareMatrix fareMatrix = fareMatrixRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fare matrix not found"));
        fareMatrix.setActive(status);
        fareMatrixRepository.save(fareMatrix);
        return mapToResponse(fareMatrix);
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
        if (stationClient.getStationRouteById(startStationId).getData() ==null ) {
            throw new EntityNotFoundException("Station not found with id: " + startStationId);
        }
        List<FareMatrix> fareMatrices = (List<FareMatrix>) fareMatrixRepository.findByStartStationIdAndIsActiveTrue(startStationId);
        return fareMatrices.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<FareMatrixResponse> getFareMatricesByEndStationIdorStartId(Long stationId) {
        if (stationClient.getStationRouteById(stationId).getData() ==null ) {
            throw new EntityNotFoundException("Station not found with id: " + stationId);
        }
        List<FareMatrix> fareMatrices = (List<FareMatrix>) fareMatrixRepository.findByStationId(stationId);
        return fareMatrices.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public boolean isStationInFareMatrix(Long stationId, Long fareMatrixId) {
        FareMatrix fareMatrixEntity = fareMatrixRepository.findById(fareMatrixId)
                .orElseThrow(() -> new EntityNotFoundException("Fare matrix not found"));
        if(!fareMatrixEntity.isActive()) {
            throw new EntityNotFoundException("Fare matrix is not active or deleted");
        }
        StationRouteResponse stationRouteResponse = stationClient.getStationRouteById(stationId).getData();
        if (stationRouteResponse == null) {
            throw new EntityNotFoundException("Station not found with id: " + stationId);
        }
        if(stationRouteResponse.isDeleted() || stationRouteResponse.status()!= StationStatus.active) {
            throw new EntityNotFoundException("This station is closed");
        }
        long startStationId = fareMatrixEntity.getStartStationId();
        long endStationId = fareMatrixEntity.getEndStationId();
        ApiResponse<Boolean> isOnLine = stationClient.checkStationOnLine(startStationId, endStationId, stationId);
        if(isOnLine.getData() == null|| !isOnLine.getData()) {
            throw new EntityNotFoundException(isOnLine.getMessage());
        }
        return stationClient.checkStationOnLine(startStationId,endStationId, stationId).getData();
    }

    @Override
    public FareMatrixResponse getFareMatrixByStations(FindFareRequest findFareRequest) {
        Long startStationId = findFareRequest.startStationId();
        Long endStationId = findFareRequest.endStationId();
        if (stationClient.getStationRouteById(startStationId).getData() == null) {
            throw new EntityNotFoundException("Start station not found with id: " + startStationId);
        }
        if (stationClient.getStationRouteById(endStationId).getData() == null) {
            throw new EntityNotFoundException("End station not found with id: " + endStationId);
        }
        FareMatrix fareMatrix = fareMatrixRepository.findByStartStationIdAndEndStationIdAndIsActiveTrue(startStationId, endStationId);
        if (fareMatrix == null) {
            throw new EntityNotFoundException("Fare matrix not found for the given station pair");
        }
        return mapToResponse(fareMatrix);
    }

    private FareMatrixResponse mapToResponse(FareMatrix fareMatrix) {
        return FareMatrixResponse.builder()
                .fareMatrixId(fareMatrix.getFareMatrixId())
                .distanceInKm(fareMatrix.getDistanceInKm())
                .price(fareMatrix.getFarePricing().getPrice())
                .name(fareMatrix.getName())
                .startStationId(fareMatrix.getStartStationId())
                .endStationId(fareMatrix.getEndStationId())
                .isActive(fareMatrix.isActive())
                .createdAt(fareMatrix.getCreatedAt())
                .updatedAt(fareMatrix.getUpdatedAt())
                .build();
    }

    private FarePricing calculateDistanceInMiles(Long startStationId, Long endStationId) {
        int distanceKm = calculateDistanceInKm(startStationId, endStationId);
        return farePricingRepository.findByIsActiveTrueOrderByMinDistanceKmAsc()
                .stream()
                .filter(rule -> distanceKm >= rule.getMinDistanceKm() && distanceKm <= rule.getMaxDistanceKm())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No pricing found"));
    }

    private int calculateDistanceInKm(Long startStationId, Long endStationId) {
        ApiResponse<StationRouteResponse> startStationResponse= stationClient.getStationRouteById(startStationId);
        if(startStationResponse.getData() == null) {
            throw new EntityNotFoundException(startStationResponse.getMessage());
        }

        ApiResponse<StationRouteResponse> endStationResponse = stationClient.getStationRouteById(endStationId);
        StationRouteResponse startStation = startStationResponse.getData();
        StationRouteResponse endStation = endStationResponse.getData();
        if (startStation == null || endStation == null) {
            throw new EntityNotFoundException("One or both stations not found");
        }

        final int R = 6371;

        double lat1 = startStation.stationsResponse().latitude();
        double lon1 = startStation.stationsResponse().longitude();
        double lat2 = endStation.stationsResponse().latitude();
        double lon2 = endStation.stationsResponse().longitude();

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanceKm = R * c;
        return (int) Math.ceil(distanceKm);
    }


}
