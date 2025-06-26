package org.alfred.ticketservice.config;

import lombok.RequiredArgsConstructor;
import org.alfred.ticketservice.model.FareMatrix;
import org.alfred.ticketservice.model.TicketTypes;
import org.alfred.ticketservice.model.enums.Duration;
import org.alfred.ticketservice.repository.FareMatrixRepository;
import org.alfred.ticketservice.repository.TicketTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TicketTypeRepository ticketTypeRepository;
    private final FareMatrixRepository fareMatrixRepository;

    @Override
    public void run(String... args) {
        initTicketTypes();
        initFareMatrix();
    }

    private void initTicketTypes() {
        if (ticketTypeRepository.count() > 0) return;

        List<TicketTypes> ticketTypes = List.of(
                TicketTypes.builder()
                        .name("One Day")
                        .description("Vé 1 ngày")
                        .validityDuration(Duration.ONE_DAY) // 1 day in minutes
                        .price(20000f)
                        .isActive(true)
                        .build(),
                TicketTypes.builder()
                        .name("Three Days")
                        .description("Vé 3 ngày")
                        .validityDuration(Duration.THREE_DAYS) // 3 days in minutes
                        .price(50000f)
                        .isActive(true)
                        .build(),
                TicketTypes.builder()
                        .name("One Week")
                        .description("Vé tuần")
                        .validityDuration(Duration.ONE_WEEK) // 7 days in minutes
                        .price(100000f)
                        .isActive(true)
                        .build(),
                TicketTypes.builder()
                        .name("One Month")
                        .description("Vé tháng")
                        .validityDuration(Duration.ONE_MONTH) // 30 days in minutes
                        .price(300000f)
                        .isActive(true)
                        .build(),
                TicketTypes.builder()
                        .name("Single")
                        .description("Vé đơn")
                        .validityDuration(Duration.SINGLE) // 30 days in minutes
                        .price(0f)
                        .isActive(true)
                        .build()
        );
        ticketTypeRepository.saveAll(ticketTypes);
    }

    private void initFareMatrix() {
        if (fareMatrixRepository.count() > 0) return;

        Long bxmdId = 1L; // Ben Xe Mien Dong ID
        Long tduId = 2L;  // Thu Duc ID
        Long btId = 3L;   // Binh Thanh ID

        List<FareMatrix> fares = List.of(
                FareMatrix.builder()
                        .name("BXMD-TDU")
                        .startStationId(bxmdId)
                        .endStationId(tduId)
                        .price(6000f)
                        .isActive(true)
                        .build(),
                FareMatrix.builder()
                        .name("BXMD-BT")
                        .startStationId(bxmdId)
                        .endStationId(btId)
                        .price(15000f)
                        .isActive(true)
                        .build(),
                FareMatrix.builder()
                        .name("TDU-BXMD")
                        .startStationId(tduId)
                        .endStationId(bxmdId)
                        .price(6000f)
                        .isActive(true)
                        .build(),
                FareMatrix.builder()
                        .name("TDU-BT")
                        .startStationId(tduId)
                        .endStationId(btId)
                        .price(9000f)
                        .isActive(true)
                        .build(),
                FareMatrix.builder()
                        .name("BT-BXMD")
                        .startStationId(btId)
                        .endStationId(bxmdId)
                        .price(15000f)
                        .isActive(true)
                        .build(),
                FareMatrix.builder()
                        .name("BT-TDU")
                        .startStationId(btId)
                        .endStationId(tduId)
                        .price(9000f)
                        .isActive(true)
                        .build()
        );

        fareMatrixRepository.saveAll(fares);
    }
}
