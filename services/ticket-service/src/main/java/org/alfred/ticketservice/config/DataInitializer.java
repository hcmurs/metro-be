package org.alfred.ticketservice.config;

import lombok.RequiredArgsConstructor;
import org.alfred.ticketservice.dto.fare_matrix.FareMatrixRequest;
import org.alfred.ticketservice.model.FareMatrix;
import org.alfred.ticketservice.model.FarePricing;
import org.alfred.ticketservice.model.TicketTypes;
import org.alfred.ticketservice.model.enums.Duration;
import org.alfred.ticketservice.repository.FareMatrixRepository;
import org.alfred.ticketservice.repository.FarePricingRepository;
import org.alfred.ticketservice.repository.TicketTypeRepository;
import org.alfred.ticketservice.service.FareMatrixService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TicketTypeRepository ticketTypeRepository;
    private final FareMatrixRepository fareMatrixRepository;
    private final FarePricingRepository farePricingRepository;
    private final FareMatrixService fareMatrixService;

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
                        .validityDuration(1) // 1 day in minutes
                        .price(20000f)
                        .isActive(true)
                        .build(),
                TicketTypes.builder()
                        .name("Three Days")
                        .description("Vé 3 ngày")
                        .validityDuration(3) // 3 days in minutes
                        .price(50000f)
                        .isActive(true)
                        .build(),
                TicketTypes.builder()
                        .name("One Week")
                        .description("Vé tuần")
                        .validityDuration(7) // 7 days in minutes
                        .price(100000f)
                        .isActive(true)
                        .build(),
                TicketTypes.builder()
                        .name("One Month")
                        .description("Vé tháng")
                        .validityDuration(30) // 30 days in minutes
                        .price(300000f)
                        .isActive(true)
                        .build(),
                TicketTypes.builder()
                        .name("Single")
                        .description("Vé đơn")
                        .validityDuration(0) // 30 days in minutes
                        .price(0f)
                        .isActive(true)
                        .build(),
                TicketTypes.builder()
                        .name("Student")
                        .description("Vé sinh viên")
                        .validityDuration(30) // 30 days in minutes
                        .forStudent(true)
                        .price(150000f)
                        .isActive(true)
                        .build()
        );
        ticketTypeRepository.saveAll(ticketTypes);
    }

    private void initFareMatrix() {
        if(farePricingRepository.count() > 0) return;
        List<FarePricing> list = List.of(
                FarePricing.builder()
                .minDistanceKm(0)
                .maxDistanceKm(5)
                .price(12000f)
                .isActive(true)
                .build(),
                FarePricing.builder()
                        .minDistanceKm(5)
                        .maxDistanceKm(10)
                        .price(15000f)
                        .isActive(true)
                        .build(),
                FarePricing.builder()
                        .minDistanceKm(10)
                        .maxDistanceKm(15)
                        .price(18000f)
                        .isActive(true)
                        .build(),
                FarePricing.builder()
                        .minDistanceKm(15)
                        .maxDistanceKm(20)
                        .price(21000f)
                        .isActive(true)
                        .build(),
                FarePricing.builder()
                        .minDistanceKm(20)
                        .maxDistanceKm(25)
                        .price(24000f)
                        .isActive(true)
                        .build(),
                FarePricing.builder()
                        .minDistanceKm(25)
                        .maxDistanceKm(30)
                        .price(27000f)
                        .isActive(true)
                        .build()
                );
        farePricingRepository.saveAll(list);


        if (fareMatrixRepository.count() > 0) return;

        Long bxmdId = 1L;
        Long stId = 2L;
        Long cncId = 3L;
        Long tduId = 4L;
        Long bthId = 5L;
        Long plId = 6L;
        Long rcId = 7L;
        Long apId = 8L;
        Long tdId = 9L;
        Long tcId = 10L;
        Long cvvt = 11L;
        Long bsId = 12L;
        Long nhtpId = 13L;
        Long btId = 14L;


        List<FareMatrixRequest> fares = List.of(
                // From BXMD (1)
                FareMatrixRequest.builder().name("BXMD-ST").startStationId(bxmdId).endStationId(stId).isActive(true).build(),
                FareMatrixRequest.builder().name("BXMD-CNC").startStationId(bxmdId).endStationId(cncId).isActive(true).build(),
                FareMatrixRequest.builder().name("BXMD-TDU").startStationId(bxmdId).endStationId(tduId).isActive(true).build(),
                FareMatrixRequest.builder().name("BXMD-BTH").startStationId(bxmdId).endStationId(bthId).isActive(true).build(),
                FareMatrixRequest.builder().name("BXMD-PL").startStationId(bxmdId).endStationId(plId).isActive(true).build(),
                FareMatrixRequest.builder().name("BXMD-RC").startStationId(bxmdId).endStationId(rcId).isActive(true).build(),
                FareMatrixRequest.builder().name("BXMD-AP").startStationId(bxmdId).endStationId(apId).isActive(true).build(),
                FareMatrixRequest.builder().name("BXMD-TD").startStationId(bxmdId).endStationId(tdId).isActive(true).build(),
                FareMatrixRequest.builder().name("BXMD-TC").startStationId(bxmdId).endStationId(tcId).isActive(true).build(),
                FareMatrixRequest.builder().name("BXMD-CVVT").startStationId(bxmdId).endStationId(cvvt).isActive(true).build(),
                FareMatrixRequest.builder().name("BXMD-BS").startStationId(bxmdId).endStationId(bsId).isActive(true).build(),
                FareMatrixRequest.builder().name("BXMD-NHTP").startStationId(bxmdId).endStationId(nhtpId).isActive(true).build(),
                FareMatrixRequest.builder().name("BXMD-BT").startStationId(bxmdId).endStationId(btId).isActive(true).build(),

                // From ST (2)
                FareMatrixRequest.builder().name("ST-BXMD").startStationId(stId).endStationId(bxmdId).isActive(true).build(),
                FareMatrixRequest.builder().name("ST-CNC").startStationId(stId).endStationId(cncId).isActive(true).build(),
                FareMatrixRequest.builder().name("ST-TDU").startStationId(stId).endStationId(tduId).isActive(true).build(),
                FareMatrixRequest.builder().name("ST-BTH").startStationId(stId).endStationId(bthId).isActive(true).build(),
                FareMatrixRequest.builder().name("ST-PL").startStationId(stId).endStationId(plId).isActive(true).build(),
                FareMatrixRequest.builder().name("ST-RC").startStationId(stId).endStationId(rcId).isActive(true).build(),
                FareMatrixRequest.builder().name("ST-AP").startStationId(stId).endStationId(apId).isActive(true).build(),
                FareMatrixRequest.builder().name("ST-TD").startStationId(stId).endStationId(tdId).isActive(true).build(),
                FareMatrixRequest.builder().name("ST-TC").startStationId(stId).endStationId(tcId).isActive(true).build(),
                FareMatrixRequest.builder().name("ST-CVVT").startStationId(stId).endStationId(cvvt).isActive(true).build(),
                FareMatrixRequest.builder().name("ST-BS").startStationId(stId).endStationId(bsId).isActive(true).build(),
                FareMatrixRequest.builder().name("ST-NHTP").startStationId(stId).endStationId(nhtpId).isActive(true).build(),
                FareMatrixRequest.builder().name("ST-BT").startStationId(stId).endStationId(btId).isActive(true).build(),

                // From CNC (3)
                FareMatrixRequest.builder().name("CNC-BXMD").startStationId(cncId).endStationId(bxmdId).isActive(true).build(),
                FareMatrixRequest.builder().name("CNC-ST").startStationId(cncId).endStationId(stId).isActive(true).build(),
                FareMatrixRequest.builder().name("CNC-TDU").startStationId(cncId).endStationId(tduId).isActive(true).build(),
                FareMatrixRequest.builder().name("CNC-BTH").startStationId(cncId).endStationId(bthId).isActive(true).build(),
                FareMatrixRequest.builder().name("CNC-PL").startStationId(cncId).endStationId(plId).isActive(true).build(),
                FareMatrixRequest.builder().name("CNC-RC").startStationId(cncId).endStationId(rcId).isActive(true).build(),
                FareMatrixRequest.builder().name("CNC-AP").startStationId(cncId).endStationId(apId).isActive(true).build(),
                FareMatrixRequest.builder().name("CNC-TD").startStationId(cncId).endStationId(tdId).isActive(true).build(),
                FareMatrixRequest.builder().name("CNC-TC").startStationId(cncId).endStationId(tcId).isActive(true).build(),
                FareMatrixRequest.builder().name("CNC-CVVT").startStationId(cncId).endStationId(cvvt).isActive(true).build(),
                FareMatrixRequest.builder().name("CNC-BS").startStationId(cncId).endStationId(bsId).isActive(true).build(),
                FareMatrixRequest.builder().name("CNC-NHTP").startStationId(cncId).endStationId(nhtpId).isActive(true).build(),
                FareMatrixRequest.builder().name("CNC-BT").startStationId(cncId).endStationId(btId).isActive(true).build(),

                // From TDU (4)
                FareMatrixRequest.builder().name("TDU-BXMD").startStationId(tduId).endStationId(bxmdId).isActive(true).build(),
                FareMatrixRequest.builder().name("TDU-ST").startStationId(tduId).endStationId(stId).isActive(true).build(),
                FareMatrixRequest.builder().name("TDU-CNC").startStationId(tduId).endStationId(cncId).isActive(true).build(),
                FareMatrixRequest.builder().name("TDU-BTH").startStationId(tduId).endStationId(bthId).isActive(true).build(),
                FareMatrixRequest.builder().name("TDU-PL").startStationId(tduId).endStationId(plId).isActive(true).build(),
                FareMatrixRequest.builder().name("TDU-RC").startStationId(tduId).endStationId(rcId).isActive(true).build(),
                FareMatrixRequest.builder().name("TDU-AP").startStationId(tduId).endStationId(apId).isActive(true).build(),
                FareMatrixRequest.builder().name("TDU-TD").startStationId(tduId).endStationId(tdId).isActive(true).build(),
                FareMatrixRequest.builder().name("TDU-TC").startStationId(tduId).endStationId(tcId).isActive(true).build(),
                FareMatrixRequest.builder().name("TDU-CVVT").startStationId(tduId).endStationId(cvvt).isActive(true).build(),
                FareMatrixRequest.builder().name("TDU-BS").startStationId(tduId).endStationId(bsId).isActive(true).build(),
                FareMatrixRequest.builder().name("TDU-NHTP").startStationId(tduId).endStationId(nhtpId).isActive(true).build(),
                FareMatrixRequest.builder().name("TDU-BT").startStationId(tduId).endStationId(btId).isActive(true).build(),

                // From BTH (5)
                FareMatrixRequest.builder().name("BTH-BXMD").startStationId(bthId).endStationId(bxmdId).isActive(true).build(),
                FareMatrixRequest.builder().name("BTH-ST").startStationId(bthId).endStationId(stId).isActive(true).build(),
                FareMatrixRequest.builder().name("BTH-CNC").startStationId(bthId).endStationId(cncId).isActive(true).build(),
                FareMatrixRequest.builder().name("BTH-TDU").startStationId(bthId).endStationId(tduId).isActive(true).build(),
                FareMatrixRequest.builder().name("BTH-PL").startStationId(bthId).endStationId(plId).isActive(true).build(),
                FareMatrixRequest.builder().name("BTH-RC").startStationId(bthId).endStationId(rcId).isActive(true).build(),
                FareMatrixRequest.builder().name("BTH-AP").startStationId(bthId).endStationId(apId).isActive(true).build(),
                FareMatrixRequest.builder().name("BTH-TD").startStationId(bthId).endStationId(tdId).isActive(true).build(),
                FareMatrixRequest.builder().name("BTH-TC").startStationId(bthId).endStationId(tcId).isActive(true).build(),
                FareMatrixRequest.builder().name("BTH-CVVT").startStationId(bthId).endStationId(cvvt).isActive(true).build(),
                FareMatrixRequest.builder().name("BTH-BS").startStationId(bthId).endStationId(bsId).isActive(true).build(),
                FareMatrixRequest.builder().name("BTH-NHTP").startStationId(bthId).endStationId(nhtpId).isActive(true).build(),
                FareMatrixRequest.builder().name("BTH-BT").startStationId(bthId).endStationId(btId).isActive(true).build(),

                // From PL (6)
                FareMatrixRequest.builder().name("PL-BXMD").startStationId(plId).endStationId(bxmdId).isActive(true).build(),
                FareMatrixRequest.builder().name("PL-ST").startStationId(plId).endStationId(stId).isActive(true).build(),
                FareMatrixRequest.builder().name("PL-CNC").startStationId(plId).endStationId(cncId).isActive(true).build(),
                FareMatrixRequest.builder().name("PL-TDU").startStationId(plId).endStationId(tduId).isActive(true).build(),
                FareMatrixRequest.builder().name("PL-BTH").startStationId(plId).endStationId(bthId).isActive(true).build(),
                FareMatrixRequest.builder().name("PL-RC").startStationId(plId).endStationId(rcId).isActive(true).build(),
                FareMatrixRequest.builder().name("PL-AP").startStationId(plId).endStationId(apId).isActive(true).build(),
                FareMatrixRequest.builder().name("PL-TD").startStationId(plId).endStationId(tdId).isActive(true).build(),
                FareMatrixRequest.builder().name("PL-TC").startStationId(plId).endStationId(tcId).isActive(true).build(),
                FareMatrixRequest.builder().name("PL-CVVT").startStationId(plId).endStationId(cvvt).isActive(true).build(),
                FareMatrixRequest.builder().name("PL-BS").startStationId(plId).endStationId(bsId).isActive(true).build(),
                FareMatrixRequest.builder().name("PL-NHTP").startStationId(plId).endStationId(nhtpId).isActive(true).build(),
                FareMatrixRequest.builder().name("PL-BT").startStationId(plId).endStationId(btId).isActive(true).build(),

                // From RC (7)
                FareMatrixRequest.builder().name("RC-BXMD").startStationId(rcId).endStationId(bxmdId).isActive(true).build(),
                FareMatrixRequest.builder().name("RC-ST").startStationId(rcId).endStationId(stId).isActive(true).build(),
                FareMatrixRequest.builder().name("RC-CNC").startStationId(rcId).endStationId(cncId).isActive(true).build(),
                FareMatrixRequest.builder().name("RC-TDU").startStationId(rcId).endStationId(tduId).isActive(true).build(),
                FareMatrixRequest.builder().name("RC-BTH").startStationId(rcId).endStationId(bthId).isActive(true).build(),
                FareMatrixRequest.builder().name("RC-PL").startStationId(rcId).endStationId(plId).isActive(true).build(),
                FareMatrixRequest.builder().name("RC-AP").startStationId(rcId).endStationId(apId).isActive(true).build(),
                FareMatrixRequest.builder().name("RC-TD").startStationId(rcId).endStationId(tdId).isActive(true).build(),
                FareMatrixRequest.builder().name("RC-TC").startStationId(rcId).endStationId(tcId).isActive(true).build(),
                FareMatrixRequest.builder().name("RC-CVVT").startStationId(rcId).endStationId(cvvt).isActive(true).build(),
                FareMatrixRequest.builder().name("RC-BS").startStationId(rcId).endStationId(bsId).isActive(true).build(),
                FareMatrixRequest.builder().name("RC-NHTP").startStationId(rcId).endStationId(nhtpId).isActive(true).build(),
                FareMatrixRequest.builder().name("RC-BT").startStationId(rcId).endStationId(btId).isActive(true).build(),

                // From AP (8)
                FareMatrixRequest.builder().name("AP-BXMD").startStationId(apId).endStationId(bxmdId).isActive(true).build(),
                FareMatrixRequest.builder().name("AP-ST").startStationId(apId).endStationId(stId).isActive(true).build(),
                FareMatrixRequest.builder().name("AP-CNC").startStationId(apId).endStationId(cncId).isActive(true).build(),
                FareMatrixRequest.builder().name("AP-TDU").startStationId(apId).endStationId(tduId).isActive(true).build(),
                FareMatrixRequest.builder().name("AP-BTH").startStationId(apId).endStationId(bthId).isActive(true).build(),
                FareMatrixRequest.builder().name("AP-PL").startStationId(apId).endStationId(plId).isActive(true).build(),
                FareMatrixRequest.builder().name("AP-RC").startStationId(apId).endStationId(rcId).isActive(true).build(),
                FareMatrixRequest.builder().name("AP-TD").startStationId(apId).endStationId(tdId).isActive(true).build(),
                FareMatrixRequest.builder().name("AP-TC").startStationId(apId).endStationId(tcId).isActive(true).build(),
                FareMatrixRequest.builder().name("AP-CVVT").startStationId(apId).endStationId(cvvt).isActive(true).build(),
                FareMatrixRequest.builder().name("AP-BS").startStationId(apId).endStationId(bsId).isActive(true).build(),
                FareMatrixRequest.builder().name("AP-NHTP").startStationId(apId).endStationId(nhtpId).isActive(true).build(),
                FareMatrixRequest.builder().name("AP-BT").startStationId(apId).endStationId(btId).isActive(true).build(),

                // From TD (9)
                FareMatrixRequest.builder().name("TD-BXMD").startStationId(tdId).endStationId(bxmdId).isActive(true).build(),
                FareMatrixRequest.builder().name("TD-ST").startStationId(tdId).endStationId(stId).isActive(true).build(),
                FareMatrixRequest.builder().name("TD-CNC").startStationId(tdId).endStationId(cncId).isActive(true).build(),
                FareMatrixRequest.builder().name("TD-TDU").startStationId(tdId).endStationId(tduId).isActive(true).build(),
                FareMatrixRequest.builder().name("TD-BTH").startStationId(tdId).endStationId(bthId).isActive(true).build(),
                FareMatrixRequest.builder().name("TD-PL").startStationId(tdId).endStationId(plId).isActive(true).build(),
                FareMatrixRequest.builder().name("TD-RC").startStationId(tdId).endStationId(rcId).isActive(true).build(),
                FareMatrixRequest.builder().name("TD-AP").startStationId(tdId).endStationId(apId).isActive(true).build(),
                FareMatrixRequest.builder().name("TD-TC").startStationId(tdId).endStationId(tcId).isActive(true).build(),
                FareMatrixRequest.builder().name("TD-CVVT").startStationId(tdId).endStationId(cvvt).isActive(true).build(),
                FareMatrixRequest.builder().name("TD-BS").startStationId(tdId).endStationId(bsId).isActive(true).build(),
                FareMatrixRequest.builder().name("TD-NHTP").startStationId(tdId).endStationId(nhtpId).isActive(true).build(),
                FareMatrixRequest.builder().name("TD-BT").startStationId(tdId).endStationId(btId).isActive(true).build(),

                // From TC (10)
                FareMatrixRequest.builder().name("TC-BXMD").startStationId(tcId).endStationId(bxmdId).isActive(true).build(),
                FareMatrixRequest.builder().name("TC-ST").startStationId(tcId).endStationId(stId).isActive(true).build(),
                FareMatrixRequest.builder().name("TC-CNC").startStationId(tcId).endStationId(cncId).isActive(true).build(),
                FareMatrixRequest.builder().name("TC-TDU").startStationId(tcId).endStationId(tduId).isActive(true).build(),
                FareMatrixRequest.builder().name("TC-BTH").startStationId(tcId).endStationId(bthId).isActive(true).build(),
                FareMatrixRequest.builder().name("TC-PL").startStationId(tcId).endStationId(plId).isActive(true).build(),
                FareMatrixRequest.builder().name("TC-RC").startStationId(tcId).endStationId(rcId).isActive(true).build(),
                FareMatrixRequest.builder().name("TC-AP").startStationId(tcId).endStationId(apId).isActive(true).build(),
                FareMatrixRequest.builder().name("TC-TD").startStationId(tcId).endStationId(tdId).isActive(true).build(),
                FareMatrixRequest.builder().name("TC-CVVT").startStationId(tcId).endStationId(cvvt).isActive(true).build(),
                FareMatrixRequest.builder().name("TC-BS").startStationId(tcId).endStationId(bsId).isActive(true).build(),
                FareMatrixRequest.builder().name("TC-NHTP").startStationId(tcId).endStationId(nhtpId).isActive(true).build(),
                FareMatrixRequest.builder().name("TC-BT").startStationId(tcId).endStationId(btId).isActive(true).build(),

                // From CVVT (11)
                FareMatrixRequest.builder().name("CVVT-BXMD").startStationId(cvvt).endStationId(bxmdId).isActive(true).build(),
                FareMatrixRequest.builder().name("CVVT-ST").startStationId(cvvt).endStationId(stId).isActive(true).build(),
                FareMatrixRequest.builder().name("CVVT-CNC").startStationId(cvvt).endStationId(cncId).isActive(true).build(),
                FareMatrixRequest.builder().name("CVVT-TDU").startStationId(cvvt).endStationId(tduId).isActive(true).build(),
                FareMatrixRequest.builder().name("CVVT-BTH").startStationId(cvvt).endStationId(bthId).isActive(true).build(),
                FareMatrixRequest.builder().name("CVVT-PL").startStationId(cvvt).endStationId(plId).isActive(true).build(),
                FareMatrixRequest.builder().name("CVVT-RC").startStationId(cvvt).endStationId(rcId).isActive(true).build(),
                FareMatrixRequest.builder().name("CVVT-AP").startStationId(cvvt).endStationId(apId).isActive(true).build(),
                FareMatrixRequest.builder().name("CVVT-TD").startStationId(cvvt).endStationId(tdId).isActive(true).build(),
                FareMatrixRequest.builder().name("CVVT-TC").startStationId(cvvt).endStationId(tcId).isActive(true).build(),
                FareMatrixRequest.builder().name("CVVT-BS").startStationId(cvvt).endStationId(bsId).isActive(true).build(),
                FareMatrixRequest.builder().name("CVVT-NHTP").startStationId(cvvt).endStationId(nhtpId).isActive(true).build(),
                FareMatrixRequest.builder().name("CVVT-BT").startStationId(cvvt).endStationId(btId).isActive(true).build(),

                // From BS (12)
                FareMatrixRequest.builder().name("BS-BXMD").startStationId(bsId).endStationId(bxmdId).isActive(true).build(),
                FareMatrixRequest.builder().name("BS-ST").startStationId(bsId).endStationId(stId).isActive(true).build(),
                FareMatrixRequest.builder().name("BS-CNC").startStationId(bsId).endStationId(cncId).isActive(true).build(),
                FareMatrixRequest.builder().name("BS-TDU").startStationId(bsId).endStationId(tduId).isActive(true).build(),
                FareMatrixRequest.builder().name("BS-BTH").startStationId(bsId).endStationId(bthId).isActive(true).build(),
                FareMatrixRequest.builder().name("BS-PL").startStationId(bsId).endStationId(plId).isActive(true).build(),
                FareMatrixRequest.builder().name("BS-RC").startStationId(bsId).endStationId(rcId).isActive(true).build(),
                FareMatrixRequest.builder().name("BS-AP").startStationId(bsId).endStationId(apId).isActive(true).build(),
                FareMatrixRequest.builder().name("BS-TD").startStationId(bsId).endStationId(tdId).isActive(true).build(),
                FareMatrixRequest.builder().name("BS-TC").startStationId(bsId).endStationId(tcId).isActive(true).build(),
                FareMatrixRequest.builder().name("BS-CVVT").startStationId(bsId).endStationId(cvvt).isActive(true).build(),
                FareMatrixRequest.builder().name("BS-NHTP").startStationId(bsId).endStationId(nhtpId).isActive(true).build(),
                FareMatrixRequest.builder().name("BS-BT").startStationId(bsId).endStationId(btId).isActive(true).build(),

                // From NHTP (13)
                FareMatrixRequest.builder().name("NHTP-BXMD").startStationId(nhtpId).endStationId(bxmdId).isActive(true).build(),
                FareMatrixRequest.builder().name("NHTP-ST").startStationId(nhtpId).endStationId(stId).isActive(true).build(),
                FareMatrixRequest.builder().name("NHTP-CNC").startStationId(nhtpId).endStationId(cncId).isActive(true).build(),
                FareMatrixRequest.builder().name("NHTP-TDU").startStationId(nhtpId).endStationId(tduId).isActive(true).build(),
                FareMatrixRequest.builder().name("NHTP-BTH").startStationId(nhtpId).endStationId(bthId).isActive(true).build(),
                FareMatrixRequest.builder().name("NHTP-PL").startStationId(nhtpId).endStationId(plId).isActive(true).build(),
                FareMatrixRequest.builder().name("NHTP-RC").startStationId(nhtpId).endStationId(rcId).isActive(true).build(),
                FareMatrixRequest.builder().name("NHTP-AP").startStationId(nhtpId).endStationId(apId).isActive(true).build(),
                FareMatrixRequest.builder().name("NHTP-TD").startStationId(nhtpId).endStationId(tdId).isActive(true).build(),
                FareMatrixRequest.builder().name("NHTP-TC").startStationId(nhtpId).endStationId(tcId).isActive(true).build(),
                FareMatrixRequest.builder().name("NHTP-CVVT").startStationId(nhtpId).endStationId(cvvt).isActive(true).build(),
                FareMatrixRequest.builder().name("NHTP-BS").startStationId(nhtpId).endStationId(bsId).isActive(true).build(),
                FareMatrixRequest.builder().name("NHTP-BT").startStationId(nhtpId).endStationId(btId).isActive(true).build(),

                // From BT (14)
                FareMatrixRequest.builder().name("BT-BXMD").startStationId(btId).endStationId(bxmdId).isActive(true).build(),
                FareMatrixRequest.builder().name("BT-ST").startStationId(btId).endStationId(stId).isActive(true).build(),
                FareMatrixRequest.builder().name("BT-CNC").startStationId(btId).endStationId(cncId).isActive(true).build(),
                FareMatrixRequest.builder().name("BT-TDU").startStationId(btId).endStationId(tduId).isActive(true).build(),
                FareMatrixRequest.builder().name("BT-BTH").startStationId(btId).endStationId(bthId).isActive(true).build(),
                FareMatrixRequest.builder().name("BT-PL").startStationId(btId).endStationId(plId).isActive(true).build(),
                FareMatrixRequest.builder().name("BT-RC").startStationId(btId).endStationId(rcId).isActive(true).build(),
                FareMatrixRequest.builder().name("BT-AP").startStationId(btId).endStationId(apId).isActive(true).build(),
                FareMatrixRequest.builder().name("BT-TD").startStationId(btId).endStationId(tdId).isActive(true).build(),
                FareMatrixRequest.builder().name("BT-TC").startStationId(btId).endStationId(tcId).isActive(true).build(),
                FareMatrixRequest.builder().name("BT-CVVT").startStationId(btId).endStationId(cvvt).isActive(true).build(),
                FareMatrixRequest.builder().name("BT-BS").startStationId(btId).endStationId(bsId).isActive(true).build(),
                FareMatrixRequest.builder().name("BT-NHTP").startStationId(btId).endStationId(nhtpId).isActive(true).build()
        );
        for( FareMatrixRequest fare : fares) {
             fareMatrixService.createFareMatrix(fare);
        }
    }
}
