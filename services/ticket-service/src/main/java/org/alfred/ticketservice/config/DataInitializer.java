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


        List<FareMatrix> fares = List.of(
                // From BXMD (1)
                FareMatrix.builder().name("BXMD-ST").startStationId(bxmdId).endStationId(stId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("BXMD-CNC").startStationId(bxmdId).endStationId(cncId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("BXMD-TDU").startStationId(bxmdId).endStationId(tduId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("BXMD-BTH").startStationId(bxmdId).endStationId(bthId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("BXMD-PL").startStationId(bxmdId).endStationId(plId).price(8000f).isActive(true).build(),
                FareMatrix.builder().name("BXMD-RC").startStationId(bxmdId).endStationId(rcId).price(9000f).isActive(true).build(),
                FareMatrix.builder().name("BXMD-AP").startStationId(bxmdId).endStationId(apId).price(10000f).isActive(true).build(),
                FareMatrix.builder().name("BXMD-TD").startStationId(bxmdId).endStationId(tdId).price(11000f).isActive(true).build(),
                FareMatrix.builder().name("BXMD-TC").startStationId(bxmdId).endStationId(tcId).price(12000f).isActive(true).build(),
                FareMatrix.builder().name("BXMD-CVVT").startStationId(bxmdId).endStationId(cvvt).price(13000f).isActive(true).build(),
                FareMatrix.builder().name("BXMD-BS").startStationId(bxmdId).endStationId(bsId).price(14000f).isActive(true).build(),
                FareMatrix.builder().name("BXMD-NHTP").startStationId(bxmdId).endStationId(nhtpId).price(15000f).isActive(true).build(),
                FareMatrix.builder().name("BXMD-BT").startStationId(bxmdId).endStationId(btId).price(15000f).isActive(true).build(),

                // From ST (2)
                FareMatrix.builder().name("ST-BXMD").startStationId(stId).endStationId(bxmdId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("ST-CNC").startStationId(stId).endStationId(cncId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("ST-TDU").startStationId(stId).endStationId(tduId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("ST-BTH").startStationId(stId).endStationId(bthId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("ST-PL").startStationId(stId).endStationId(plId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("ST-RC").startStationId(stId).endStationId(rcId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("ST-AP").startStationId(stId).endStationId(apId).price(8000f).isActive(true).build(),
                FareMatrix.builder().name("ST-TD").startStationId(stId).endStationId(tdId).price(9000f).isActive(true).build(),
                FareMatrix.builder().name("ST-TC").startStationId(stId).endStationId(tcId).price(10000f).isActive(true).build(),
                FareMatrix.builder().name("ST-CVVT").startStationId(stId).endStationId(cvvt).price(11000f).isActive(true).build(),
                FareMatrix.builder().name("ST-BS").startStationId(stId).endStationId(bsId).price(12000f).isActive(true).build(),
                FareMatrix.builder().name("ST-NHTP").startStationId(stId).endStationId(nhtpId).price(13000f).isActive(true).build(),
                FareMatrix.builder().name("ST-BT").startStationId(stId).endStationId(btId).price(14000f).isActive(true).build(),

                // From CNC (3)
                FareMatrix.builder().name("CNC-BXMD").startStationId(cncId).endStationId(bxmdId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("CNC-ST").startStationId(cncId).endStationId(stId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("CNC-TDU").startStationId(cncId).endStationId(tduId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("CNC-BTH").startStationId(cncId).endStationId(bthId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("CNC-PL").startStationId(cncId).endStationId(plId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("CNC-RC").startStationId(cncId).endStationId(rcId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("CNC-AP").startStationId(cncId).endStationId(apId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("CNC-TD").startStationId(cncId).endStationId(tdId).price(8000f).isActive(true).build(),
                FareMatrix.builder().name("CNC-TC").startStationId(cncId).endStationId(tcId).price(9000f).isActive(true).build(),
                FareMatrix.builder().name("CNC-CVVT").startStationId(cncId).endStationId(cvvt).price(10000f).isActive(true).build(),
                FareMatrix.builder().name("CNC-BS").startStationId(cncId).endStationId(bsId).price(11000f).isActive(true).build(),
                FareMatrix.builder().name("CNC-NHTP").startStationId(cncId).endStationId(nhtpId).price(12000f).isActive(true).build(),
                FareMatrix.builder().name("CNC-BT").startStationId(cncId).endStationId(btId).price(13000f).isActive(true).build(),

                // From TDU (4)
                FareMatrix.builder().name("TDU-BXMD").startStationId(tduId).endStationId(bxmdId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("TDU-ST").startStationId(tduId).endStationId(stId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("TDU-CNC").startStationId(tduId).endStationId(cncId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("TDU-BTH").startStationId(tduId).endStationId(bthId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("TDU-PL").startStationId(tduId).endStationId(plId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("TDU-RC").startStationId(tduId).endStationId(rcId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("TDU-AP").startStationId(tduId).endStationId(apId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("TDU-TD").startStationId(tduId).endStationId(tdId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("TDU-TC").startStationId(tduId).endStationId(tcId).price(8000f).isActive(true).build(),
                FareMatrix.builder().name("TDU-CVVT").startStationId(tduId).endStationId(cvvt).price(9000f).isActive(true).build(),
                FareMatrix.builder().name("TDU-BS").startStationId(tduId).endStationId(bsId).price(10000f).isActive(true).build(),
                FareMatrix.builder().name("TDU-NHTP").startStationId(tduId).endStationId(nhtpId).price(11000f).isActive(true).build(),
                FareMatrix.builder().name("TDU-BT").startStationId(tduId).endStationId(btId).price(9000f).isActive(true).build(),

                // From BTH (5)
                FareMatrix.builder().name("BTH-BXMD").startStationId(bthId).endStationId(bxmdId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("BTH-ST").startStationId(bthId).endStationId(stId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("BTH-CNC").startStationId(bthId).endStationId(cncId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("BTH-TDU").startStationId(bthId).endStationId(tduId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("BTH-PL").startStationId(bthId).endStationId(plId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("BTH-RC").startStationId(bthId).endStationId(rcId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("BTH-AP").startStationId(bthId).endStationId(apId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("BTH-TD").startStationId(bthId).endStationId(tdId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("BTH-TC").startStationId(bthId).endStationId(tcId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("BTH-CVVT").startStationId(bthId).endStationId(cvvt).price(8000f).isActive(true).build(),
                FareMatrix.builder().name("BTH-BS").startStationId(bthId).endStationId(bsId).price(9000f).isActive(true).build(),
                FareMatrix.builder().name("BTH-NHTP").startStationId(bthId).endStationId(nhtpId).price(10000f).isActive(true).build(),
                FareMatrix.builder().name("BTH-BT").startStationId(bthId).endStationId(btId).price(8000f).isActive(true).build(),

                // From PL (6)
                FareMatrix.builder().name("PL-BXMD").startStationId(plId).endStationId(bxmdId).price(8000f).isActive(true).build(),
                FareMatrix.builder().name("PL-ST").startStationId(plId).endStationId(stId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("PL-CNC").startStationId(plId).endStationId(cncId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("PL-TDU").startStationId(plId).endStationId(tduId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("PL-BTH").startStationId(plId).endStationId(bthId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("PL-RC").startStationId(plId).endStationId(rcId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("PL-AP").startStationId(plId).endStationId(apId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("PL-TD").startStationId(plId).endStationId(tdId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("PL-TC").startStationId(plId).endStationId(tcId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("PL-CVVT").startStationId(plId).endStationId(cvvt).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("PL-BS").startStationId(plId).endStationId(bsId).price(8000f).isActive(true).build(),
                FareMatrix.builder().name("PL-NHTP").startStationId(plId).endStationId(nhtpId).price(9000f).isActive(true).build(),
                FareMatrix.builder().name("PL-BT").startStationId(plId).endStationId(btId).price(7000f).isActive(true).build(),

                // From RC (7)
                FareMatrix.builder().name("RC-BXMD").startStationId(rcId).endStationId(bxmdId).price(9000f).isActive(true).build(),
                FareMatrix.builder().name("RC-ST").startStationId(rcId).endStationId(stId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("RC-CNC").startStationId(rcId).endStationId(cncId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("RC-TDU").startStationId(rcId).endStationId(tduId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("RC-BTH").startStationId(rcId).endStationId(bthId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("RC-PL").startStationId(rcId).endStationId(plId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("RC-AP").startStationId(rcId).endStationId(apId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("RC-TD").startStationId(rcId).endStationId(tdId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("RC-TC").startStationId(rcId).endStationId(tcId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("RC-CVVT").startStationId(rcId).endStationId(cvvt).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("RC-BS").startStationId(rcId).endStationId(bsId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("RC-NHTP").startStationId(rcId).endStationId(nhtpId).price(8000f).isActive(true).build(),
                FareMatrix.builder().name("RC-BT").startStationId(rcId).endStationId(btId).price(6000f).isActive(true).build(),

                // From AP (8)
                FareMatrix.builder().name("AP-BXMD").startStationId(apId).endStationId(bxmdId).price(10000f).isActive(true).build(),
                FareMatrix.builder().name("AP-ST").startStationId(apId).endStationId(stId).price(8000f).isActive(true).build(),
                FareMatrix.builder().name("AP-CNC").startStationId(apId).endStationId(cncId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("AP-TDU").startStationId(apId).endStationId(tduId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("AP-BTH").startStationId(apId).endStationId(bthId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("AP-PL").startStationId(apId).endStationId(plId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("AP-RC").startStationId(apId).endStationId(rcId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("AP-TD").startStationId(apId).endStationId(tdId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("AP-TC").startStationId(apId).endStationId(tcId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("AP-CVVT").startStationId(apId).endStationId(cvvt).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("AP-BS").startStationId(apId).endStationId(bsId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("AP-NHTP").startStationId(apId).endStationId(nhtpId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("AP-BT").startStationId(apId).endStationId(btId).price(5000f).isActive(true).build(),

                // From TD (9)
                FareMatrix.builder().name("TD-BXMD").startStationId(tdId).endStationId(bxmdId).price(11000f).isActive(true).build(),
                FareMatrix.builder().name("TD-ST").startStationId(tdId).endStationId(stId).price(9000f).isActive(true).build(),
                FareMatrix.builder().name("TD-CNC").startStationId(tdId).endStationId(cncId).price(8000f).isActive(true).build(),
                FareMatrix.builder().name("TD-TDU").startStationId(tdId).endStationId(tduId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("TD-BTH").startStationId(tdId).endStationId(bthId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("TD-PL").startStationId(tdId).endStationId(plId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("TD-RC").startStationId(tdId).endStationId(rcId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("TD-AP").startStationId(tdId).endStationId(apId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("TD-TC").startStationId(tdId).endStationId(tcId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("TD-CVVT").startStationId(tdId).endStationId(cvvt).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("TD-BS").startStationId(tdId).endStationId(bsId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("TD-NHTP").startStationId(tdId).endStationId(nhtpId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("TD-BT").startStationId(tdId).endStationId(btId).price(4000f).isActive(true).build(),

                // From TC (10)
                FareMatrix.builder().name("TC-BXMD").startStationId(tcId).endStationId(bxmdId).price(12000f).isActive(true).build(),
                FareMatrix.builder().name("TC-ST").startStationId(tcId).endStationId(stId).price(10000f).isActive(true).build(),
                FareMatrix.builder().name("TC-CNC").startStationId(tcId).endStationId(cncId).price(9000f).isActive(true).build(),
                FareMatrix.builder().name("TC-TDU").startStationId(tcId).endStationId(tduId).price(8000f).isActive(true).build(),
                FareMatrix.builder().name("TC-BTH").startStationId(tcId).endStationId(bthId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("TC-PL").startStationId(tcId).endStationId(plId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("TC-RC").startStationId(tcId).endStationId(rcId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("TC-AP").startStationId(tcId).endStationId(apId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("TC-TD").startStationId(tcId).endStationId(tdId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("TC-CVVT").startStationId(tcId).endStationId(cvvt).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("TC-BS").startStationId(tcId).endStationId(bsId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("TC-NHTP").startStationId(tcId).endStationId(nhtpId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("TC-BT").startStationId(tcId).endStationId(btId).price(3000f).isActive(true).build(),

                // From CVVT (11)
                FareMatrix.builder().name("CVVT-BXMD").startStationId(cvvt).endStationId(bxmdId).price(13000f).isActive(true).build(),
                FareMatrix.builder().name("CVVT-ST").startStationId(cvvt).endStationId(stId).price(11000f).isActive(true).build(),
                FareMatrix.builder().name("CVVT-CNC").startStationId(cvvt).endStationId(cncId).price(10000f).isActive(true).build(),
                FareMatrix.builder().name("CVVT-TDU").startStationId(cvvt).endStationId(tduId).price(9000f).isActive(true).build(),
                FareMatrix.builder().name("CVVT-BTH").startStationId(cvvt).endStationId(bthId).price(8000f).isActive(true).build(),
                FareMatrix.builder().name("CVVT-PL").startStationId(cvvt).endStationId(plId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("CVVT-RC").startStationId(cvvt).endStationId(rcId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("CVVT-AP").startStationId(cvvt).endStationId(apId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("CVVT-TD").startStationId(cvvt).endStationId(tdId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("CVVT-TC").startStationId(cvvt).endStationId(tcId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("CVVT-BS").startStationId(cvvt).endStationId(bsId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("CVVT-NHTP").startStationId(cvvt).endStationId(nhtpId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("CVVT-BT").startStationId(cvvt).endStationId(btId).price(4000f).isActive(true).build(),

                // From BS (12)
                FareMatrix.builder().name("BS-BXMD").startStationId(bsId).endStationId(bxmdId).price(14000f).isActive(true).build(),
                FareMatrix.builder().name("BS-ST").startStationId(bsId).endStationId(stId).price(12000f).isActive(true).build(),
                FareMatrix.builder().name("BS-CNC").startStationId(bsId).endStationId(cncId).price(11000f).isActive(true).build(),
                FareMatrix.builder().name("BS-TDU").startStationId(bsId).endStationId(tduId).price(10000f).isActive(true).build(),
                FareMatrix.builder().name("BS-BTH").startStationId(bsId).endStationId(bthId).price(9000f).isActive(true).build(),
                FareMatrix.builder().name("BS-PL").startStationId(bsId).endStationId(plId).price(8000f).isActive(true).build(),
                FareMatrix.builder().name("BS-RC").startStationId(bsId).endStationId(rcId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("BS-AP").startStationId(bsId).endStationId(apId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("BS-TD").startStationId(bsId).endStationId(tdId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("BS-TC").startStationId(bsId).endStationId(tcId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("BS-CVVT").startStationId(bsId).endStationId(cvvt).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("BS-NHTP").startStationId(bsId).endStationId(nhtpId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("BS-BT").startStationId(bsId).endStationId(btId).price(5000f).isActive(true).build(),

                // From NHTP (13)
                FareMatrix.builder().name("NHTP-BXMD").startStationId(nhtpId).endStationId(bxmdId).price(15000f).isActive(true).build(),
                FareMatrix.builder().name("NHTP-ST").startStationId(nhtpId).endStationId(stId).price(13000f).isActive(true).build(),
                FareMatrix.builder().name("NHTP-CNC").startStationId(nhtpId).endStationId(cncId).price(12000f).isActive(true).build(),
                FareMatrix.builder().name("NHTP-TDU").startStationId(nhtpId).endStationId(tduId).price(11000f).isActive(true).build(),
                FareMatrix.builder().name("NHTP-BTH").startStationId(nhtpId).endStationId(bthId).price(10000f).isActive(true).build(),
                FareMatrix.builder().name("NHTP-PL").startStationId(nhtpId).endStationId(plId).price(9000f).isActive(true).build(),
                FareMatrix.builder().name("NHTP-RC").startStationId(nhtpId).endStationId(rcId).price(8000f).isActive(true).build(),
                FareMatrix.builder().name("NHTP-AP").startStationId(nhtpId).endStationId(apId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("NHTP-TD").startStationId(nhtpId).endStationId(tdId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("NHTP-TC").startStationId(nhtpId).endStationId(tcId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("NHTP-CVVT").startStationId(nhtpId).endStationId(cvvt).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("NHTP-BS").startStationId(nhtpId).endStationId(bsId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("NHTP-BT").startStationId(nhtpId).endStationId(btId).price(3000f).isActive(true).build(),

                // From BT (14)
                FareMatrix.builder().name("BT-BXMD").startStationId(btId).endStationId(bxmdId).price(15000f).isActive(true).build(),
                FareMatrix.builder().name("BT-ST").startStationId(btId).endStationId(stId).price(14000f).isActive(true).build(),
                FareMatrix.builder().name("BT-CNC").startStationId(btId).endStationId(cncId).price(13000f).isActive(true).build(),
                FareMatrix.builder().name("BT-TDU").startStationId(btId).endStationId(tduId).price(9000f).isActive(true).build(),
                FareMatrix.builder().name("BT-BTH").startStationId(btId).endStationId(bthId).price(8000f).isActive(true).build(),
                FareMatrix.builder().name("BT-PL").startStationId(btId).endStationId(plId).price(7000f).isActive(true).build(),
                FareMatrix.builder().name("BT-RC").startStationId(btId).endStationId(rcId).price(6000f).isActive(true).build(),
                FareMatrix.builder().name("BT-AP").startStationId(btId).endStationId(apId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("BT-TD").startStationId(btId).endStationId(tdId).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("BT-TC").startStationId(btId).endStationId(tcId).price(3000f).isActive(true).build(),
                FareMatrix.builder().name("BT-CVVT").startStationId(btId).endStationId(cvvt).price(4000f).isActive(true).build(),
                FareMatrix.builder().name("BT-BS").startStationId(btId).endStationId(bsId).price(5000f).isActive(true).build(),
                FareMatrix.builder().name("BT-NHTP").startStationId(btId).endStationId(nhtpId).price(3000f).isActive(true).build()
        );
        fareMatrixRepository.saveAll(fares);
    }
}
