package org.alfred.ticketservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.alfred.ticketservice.client.StationClient;
import org.alfred.ticketservice.config.ApiResponse;
import org.alfred.ticketservice.dto.StationEvent;
import org.alfred.ticketservice.dto.fare_matrix.FareMatrixRequest;
import org.alfred.ticketservice.dto.fare_matrix.FareMatrixResponse;
import org.alfred.ticketservice.dto.station.StationResponse;
import org.alfred.ticketservice.dto.station.StationRouteResponse;
import org.alfred.ticketservice.model.FareMatrix;
import org.alfred.ticketservice.model.enums.StationStatus;
import org.alfred.ticketservice.repository.FareMatrixRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class TicketReactionServiceImpl implements TicketReactionService{
    private final TicketService ticketService;
    private final FareMatrixService fareMatrixService;
    private final FareMatrixRepository fareMatrixRepository;
    private final StationClient stationClient;

    @Override
    public void handleDeleteStation(StationEvent event) {
        try {
            ApiResponse<StationRouteResponse> stationRouteResponse = stationClient.getStationRouteById(event.stationRouteId());
            StationRouteResponse stationRoute = stationRouteResponse.getData();

            if (stationRoute == null) {
                log.warn("Station route not found for ID: {}", event.stationRouteId());
                return;
            }

            List<FareMatrixResponse> fareMatrixResponses = fareMatrixService.getFareMatricesByEndStationIdorStartId(
                    stationRoute.stationsResponse().stationId());


            if (fareMatrixResponses.isEmpty()) {
                log.info("No fare matrices found for station: {}", stationRoute.stationsResponse().stationId());
                return;
            }

            // Determine the target status
            boolean shouldActivate = !(stationRoute.isDeleted() || stationRoute.status() != StationStatus.active);

            // Update fare matrices in batch
            List<FareMatrix> fareMatricesToUpdate = new ArrayList<>();

            for (FareMatrixResponse fareMatrixResponse : fareMatrixResponses) {
                Optional<FareMatrix> fareMatrixOpt = fareMatrixRepository.findById(fareMatrixResponse.fareMatrixId());

                if (fareMatrixOpt.isPresent()) {
                    FareMatrix fareMatrix = fareMatrixOpt.get();
                    if (fareMatrix.isActive() != shouldActivate) {
                        fareMatrix.setActive(shouldActivate);
                        fareMatricesToUpdate.add(fareMatrix);
                    }
                } else {
                    log.warn("Fare matrix not found with ID: {}", fareMatrixResponse.fareMatrixId());
                }
            }
            if (!fareMatricesToUpdate.isEmpty()) {
                fareMatrixRepository.saveAll(fareMatricesToUpdate);
                log.info("Updated {} fare matrices for station route: {}",
                        fareMatricesToUpdate.size(), event.stationRouteId());
            }

        } catch (Exception e) {
            log.error("Error handling delete station event for station route: {}",
                    event.stationRouteId(), e);
            throw new RuntimeException("Failed to handle delete station event", e);
        }
    }
    @Override
    public void handleAddStation(StationEvent event) {
        try {
            // Lấy thông tin station route từ event
            ApiResponse<StationRouteResponse> stationRouteResponse = stationClient.getStationRouteById(event.stationRouteId());
            StationRouteResponse stationRoute = stationRouteResponse.getData();

            if (stationRoute == null) {
                log.warn("Station route not found for ID: {}", event.stationRouteId());
                return;
            }

            // Kiểm tra station route có active không
            if (stationRoute.isDeleted() || stationRoute.status() != StationStatus.active) {
                log.info("Station route is not active, skipping fare matrix creation for: {}", event.stationRouteId());
                return;
            }

            Long newStationId = stationRoute.id();

            // Lấy tất cả stations trong cùng route
            List<StationRouteResponse> allStationsInRoute = stationClient.getStationRoutesByRouteId(stationRoute.RouteId()).getData();

            if (allStationsInRoute.isEmpty()) {
                log.info("No other stations found in route: {}", stationRoute.RouteId());
                return;
            }

            List<FareMatrixRequest> fareMatricesToCreate = new ArrayList<>();
            for (StationRouteResponse otherStation : allStationsInRoute) {
                if (otherStation.id().equals(newStationId)) {
                    continue;
                }
                if (otherStation.isDeleted() || otherStation.status() != StationStatus.active) {
                    continue;
                }

                Long otherStationId = otherStation.id();

                // Tạo fare matrix từ station mới đến station khác
//                FareMatrixRequest.builder().name("NHTP-BXMD").startStationId(nhtpId).endStationId(bxmdId).isActive(true).build(),
                FareMatrixRequest fareMatrixFrom = FareMatrixRequest.builder().name(stationRoute.stationsResponse().stationCode()+"-"+ otherStation.stationsResponse().stationCode())
                        .startStationId(newStationId)
                        .endStationId(otherStationId)
                        .isActive(true)
                        .build();
                if (fareMatrixFrom != null) {
                    fareMatricesToCreate.add(fareMatrixFrom);
                }

                // Tạo fare matrix từ station khác đến station mới
                FareMatrixRequest fareMatrixTo = FareMatrixRequest.builder().name(otherStation.stationsResponse().stationCode()+"-"+ stationRoute.stationsResponse().stationCode())
                        .startStationId(otherStationId)
                        .endStationId(newStationId)
                        .isActive(true)
                        .build();
                if (fareMatrixTo != null) {
                    fareMatricesToCreate.add(fareMatrixTo);
                }
            }

            // Lưu tất cả fare matrices
            if (!fareMatricesToCreate.isEmpty()) {
                for (FareMatrixRequest fareMatrix : fareMatricesToCreate) {
                    fareMatrixService.createFareMatrix(fareMatrix);
                }
                log.info("Created {} fare matrices for new station: {} in route: {}",
                        fareMatricesToCreate.size(), newStationId, stationRoute.RouteId());
            } else {
                log.info("No fare matrices created for station: {} in route: {}",
                        newStationId, stationRoute.RouteId());
            }

        } catch (Exception e) {
            log.error("Error handling add station event for station route: {}",
                    event.stationRouteId(), e);
            throw new RuntimeException("Failed to handle add station event", e);
        }
    }
}
